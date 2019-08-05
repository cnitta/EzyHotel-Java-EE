package hms.prepostarrival.session;

import hms.common.CommonMethods;
import hms.common.CrudService;
import hms.commoninfra.session.AccountSessionLocal;
import hms.commoninfra.session.LogSessionLocal;
import hms.frontdesk.session.PaymentMethodSessionLocal;
import hms.frontdesk.session.PaymentSessionLocal;
import hms.frontdesk.session.TransactionSessionLocal;
import hms.sales.session.PriceRateSessionLocal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericEntity;
import util.entity.CustomerEntity;
import util.entity.GroupBookingEntity;
import util.entity.PaymentEntity;
import util.entity.PaymentMethodEntity;
import util.entity.PriceRateEntity;
import util.entity.RoomBookingEntity;
import util.entity.RoomTypeEntity;
import util.entity.TransactionEntity;
import util.enumeration.GroupBookingEnum;
import util.enumeration.LogStatusEnum;
import util.enumeration.RoomBookingStatusEnum;
import util.enumeration.SystemCategoryEnum;
import util.exception.CustomerProfileConflictException;
import util.exception.RoomBookingException;

/**
 *
 * @author berni
 */
@Stateless
public class OnlineRoomBookingSession implements OnlineRoomBookingSessionLocal, CrudService<RoomBookingEntity> {

    @EJB
    private PriceRateSessionLocal priceRateSessionLocal;
    @EJB
    private AccountSessionLocal accountSessionLocal;

    @EJB
    private LogSessionLocal logSessionLocal;

    @EJB
    private PaymentMethodSessionLocal paymentMethodSessionLocal;

    @EJB
    private TransactionSessionLocal transactionSessionLocal;

    @EJB
    private PaymentSessionLocal paymentSessionLocal;

    @PersistenceContext
    private EntityManager em;

    private CommonMethods commons = new CommonMethods();

    //This method will only be called when the payment has been verified
    //Replace with List<RoomBookingEntity> once complete
    @Override
    public RoomBookingEntity createSingleRoomBooking(RoomBookingEntity newRoomBooking, CustomerEntity customer, String priceRateTitle, String finalPrice, String cardHolderName,
            String hCreditCard, String creditCardExpiryDate) throws CustomerProfileConflictException, ParseException, RoomBookingException {

        String reservationNumber = generateReservationNumber();
        newRoomBooking.setReservationNumber(reservationNumber);
        newRoomBooking.setStatus(RoomBookingStatusEnum.MANUAL);
        RoomBookingEntity createRoomBooking = createEntity(newRoomBooking);
        logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.PRE_POST_ARRIVAL_MS, LogStatusEnum.CREATE, new GenericEntity(createRoomBooking, RoomBookingEntity.class), "BOOKING");

        return createRoomBooking;
    }

    @Override
    public PaymentEntity createPaymentTransactionForSingleBooking(String finalPrice, String cardHolderName, String hCreditCard, String creditCardExpiryDate,
            RoomBookingEntity newRoomBooking, CustomerEntity customer) {

        //Generate the PaymentEntity - 1 is credit card, 2 is cash
        BigDecimal finalPriceAmt = new BigDecimal(finalPrice);
        finalPriceAmt = finalPriceAmt.multiply(new BigDecimal(newRoomBooking.getNumOfDays())).setScale(2, RoundingMode.HALF_EVEN);
        PaymentEntity payment = new PaymentEntity(1, finalPriceAmt, newRoomBooking);
        payment = paymentSessionLocal.createEntity(payment);
        System.out.println("Payment: " + payment.toString());

        //PaymentMethodEntity
        YearMonth creditCardExpiry = YearMonth.parse(creditCardExpiryDate);
        Date creditCardExpiryDateForDB = convertYearMonthToDate(creditCardExpiry);
        PaymentMethodEntity paymentMethod = new PaymentMethodEntity(1, hCreditCard, cardHolderName, creditCardExpiryDateForDB, payment);
        paymentMethod = paymentMethodSessionLocal.createEntity(paymentMethod);
        System.out.println("Payment Method: " + paymentMethod.toString());

        //TransactionEntity
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();
        TransactionEntity transaction = new TransactionEntity(customer.getCustIdentity(), currentDateTime.format(dateTimeFormat), finalPriceAmt, "Paid via credit card");
        transaction.setPayment(payment);
        transaction = transactionSessionLocal.createEntity(transaction);
        System.out.println("Transaction: " + transaction.toString());

        return payment;
    }

    //To change back to the List<RoomBookingEntity> after completing the method
    @Override
    public List<RoomBookingEntity> createGoupRoomBooking(List<RoomBookingEntity> newRoomBookings, CustomerEntity customer, String cardHolderName,
            String hCreditCard, String creditCardExpiryDate, String bookingType) throws CustomerProfileConflictException, ParseException, RoomBookingException {

        String reservationNumber = generateReservationNumber();

        List<RoomBookingEntity> roomBookingsCreated = new ArrayList<>();

        for (RoomBookingEntity newRoomBooking : newRoomBookings) {
            newRoomBooking.setStatus(RoomBookingStatusEnum.MANUAL);
            newRoomBooking.setReservationNumber(reservationNumber);

            RoomBookingEntity createRoomBooking = createEntity(newRoomBooking);
            roomBookingsCreated.add(createRoomBooking);
            logSessionLocal.centralLoggingGeneration(SystemCategoryEnum.PRE_POST_ARRIVAL_MS, LogStatusEnum.CREATE, new GenericEntity(createRoomBooking, RoomBookingEntity.class), "BOOKING");
        }
        return roomBookingsCreated;
    }

    @Override
    public List<PaymentEntity> createPaymentTransactionForGroupBooking(List<RoomBookingEntity> newRoomBookings, CustomerEntity customer, String cardHolderName, String hCreditCard, String creditCardExpiryDate) {
        //The data formatters for PaymentMethod and Transaction
        //PaymentMethodEntity
        YearMonth creditCardExpiry = YearMonth.parse(creditCardExpiryDate);
        System.out.println("YearMonth of credit card expiry: " + creditCardExpiry);

        Date creditCardExpiryDateForDB = convertYearMonthToDate(creditCardExpiry);
        System.out.println("Date of credit card expiry: " + creditCardExpiryDateForDB);

        //TransactionEntity
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<PaymentEntity> payments = new ArrayList<>();

        for (RoomBookingEntity newRoomBooking : newRoomBookings) {

            RoomTypeEntity roomType = newRoomBooking.getRoomType();
            PriceRateEntity priceRateForRoomType = new PriceRateEntity();
            if (((GroupBookingEntity) newRoomBooking).getGroupBookingType().equals(GroupBookingEnum.CORPORATE)
                    && !(roomType.getRoomTypecode().equals("EXE") || roomType.getRoomTypecode().equals("PRE"))) {
                //Only Junior Suite, Deluxe and Superior have corporate rates
                priceRateForRoomType = getPriceRateByRoomType(newRoomBooking, roomType.getRoomTypeId(), "%Corporate");
            } else {
                if (newRoomBookings.size() >= 5 && !((GroupBookingEntity) newRoomBooking).getGroupBookingType().equals(GroupBookingEnum.CORPORATE)) {
                    //Minimum Rooms for the Group Rate to apply -> Applicabe to Social Group Booking only
                    priceRateForRoomType = getPriceRateByRoomType(newRoomBooking, roomType.getRoomTypeId(), "%Group");
                } else {
                    //Standard/Premier & Leisure
                    priceRateForRoomType = getPriceRateByRoomTypeDay(newRoomBooking, roomType);
                }
            }

            BigDecimal finalPriceAmt = new BigDecimal(priceRateForRoomType.getMarkupPrice());
            finalPriceAmt = finalPriceAmt.multiply(new BigDecimal(newRoomBooking.getNumOfDays())).setScale(2, RoundingMode.HALF_EVEN);
            PaymentEntity payment = new PaymentEntity(1, finalPriceAmt, newRoomBooking);

            PaymentMethodEntity paymentMethod = new PaymentMethodEntity(1, hCreditCard, cardHolderName, creditCardExpiryDateForDB, payment);

            TransactionEntity transaction = new TransactionEntity(customer.getCustIdentity(), currentDateTime.format(dateTimeFormat), finalPriceAmt, "Paid via credit card");
            transaction.setPayment(payment);

            //Create the Payment, PaymentMethod, Transaction
            payment = paymentSessionLocal.createEntity(payment);
            paymentMethod = paymentMethodSessionLocal.createEntity(paymentMethod);
            transaction = transactionSessionLocal.createEntity(transaction);

            payments.add(payment);
        }
        return payments;

    }

    private PriceRateEntity getPriceRateByRoomTypeDay(RoomBookingEntity newRoomBooking, RoomTypeEntity roomType) {
        PriceRateEntity priceRateForRoomType = new PriceRateEntity();

        int day = LocalDate.now().getDayOfWeek().getValue();
        System.out.println("Today is: " + day);
        if (day >= 6) { //Weekend
            System.out.println("IN WEEKENDS");
            priceRateForRoomType = getPriceRateByRoomType(newRoomBooking, newRoomBooking.getRoomType().getRoomTypeId(), "%Leisure");

        } else { //Weekdays
            //Rooms with different rate title
            System.out.println("IN WEEKDAYS");
            if (roomType.getName().contains("Executive") || roomType.getName().contains("President")) {
                priceRateForRoomType = getPriceRateByRoomType(newRoomBooking, newRoomBooking.getRoomType().getRoomTypeId(), "%Premier");

            } else {
                System.out.println("roomType name: " + roomType.getName());
                priceRateForRoomType = getPriceRateByRoomType(newRoomBooking, newRoomBooking.getRoomType().getRoomTypeId(), "%Standard");
            }
        }

        return priceRateForRoomType;

    }

    @Override
    public void updateRoomBooking(RoomBookingEntity roomBooking) {
        updateEntity(roomBooking);
    }

    @Override
    public PaymentEntity getPaymentByBookingId(Long bookingId) throws Exception {
        try {
            PaymentEntity payment = paymentSessionLocal.retrieveEntityById("roomBooking.bookingId", bookingId);
            return payment;
        } catch (Exception e) {
            throw new Exception("Unable to retrieve the payment amount for booking.");
        }
    }

    @Override
    public List<RoomBookingEntity> retrieveBookingsByReservationNumber(String reservationNumber) {
        String queryInput = "SELECT c FROM RoomBookingEntity c WHERE c.reservationNumber =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", reservationNumber);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    private PriceRateEntity getPriceRateByRoomType(RoomBookingEntity roomBooking, Long roomTypeId, String rateTitle) {

        String queryInput = "SELECT c FROM PriceRateEntity c WHERE c.roomType.roomTypeId =:inValueA AND c.rateTitle LIKE :inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", roomTypeId);
        query.setParameter("inValueB", rateTitle);

        try {
            return (PriceRateEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }

    }

    private String generateReservationNumber() {

        String reservationNumber = "";
        boolean checkReservationNumber = false;
        do {
            reservationNumber = commons.generateRandomAlphanumeric(4);
            try {
                //Check if reservation number already exists in the DB
                String getReservationNumber = retrieveEntity("reservationNumber", reservationNumber).getReservationNumber();
            } catch (NotFoundException e) {
                checkReservationNumber = true;
            }

        } while (!checkReservationNumber);
        return reservationNumber;
    }

    private Date convertYearMonthToDate(YearMonth yearMonth) {
        return Date.valueOf(yearMonth.atDay(1));
    }

    private YearMonth convertFromDBToYearMonth(Date dbYearMonth) {
        return YearMonth.from(Instant.ofEpochMilli(dbYearMonth.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /*================ Codes generated with Generated Method Body Template ========================*/
    @Override
    public RoomBookingEntity createEntity(RoomBookingEntity entity) {
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        return entity;
    }

    @Override
    public RoomBookingEntity retrieveEntityById(Long id) {
        RoomBookingEntity entity = em.find(RoomBookingEntity.class, id);
        if (entity != null) {
            return entity;
        } else {
            throw new NotFoundException("RoomBookingEntity ID " + id + " does not exist!");
        }
    }

    @Override
    public List<RoomBookingEntity> retrieveAllEntities() {
        Query query = em.createQuery("SELECT c FROM RoomBookingEntity c", RoomBookingEntity.class);
        return query.getResultList();
    }

    @Override
    public void deleteEntity(Long id) {
        RoomBookingEntity entity = retrieveEntityById(id);
        em.remove(entity);
    }

    @Override
    public void updateEntity(RoomBookingEntity entity) {
        em.merge(entity);
    }

    @Override
    public RoomBookingEntity retrieveEntity(String attribute, String value) {
        String queryInput = "SELECT c FROM RoomBookingEntity c WHERE c." + attribute + " =:inValue ";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValue", value);
        try {
            return (RoomBookingEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomBookingEntity> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM RoomBookingEntity c WHERE c." + attributeA + " =:inValueA AND c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }
    }

    @Override
    public List<RoomBookingEntity> retrieveEntityByEitherFilters(String attributeA, String valueA, String attributeB, String valueB) {
        String queryInput = "SELECT c FROM RoomBookingEntity c WHERE c." + attributeA + " =:inValueA OR c." + attributeB + " =:inValueB";
        Query query = em.createQuery(queryInput);
        query.setParameter("inValueA", valueA);
        query.setParameter("inValueB", valueB);
        try {
            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new NotFoundException("Entity not found!");
        }

    }

}
