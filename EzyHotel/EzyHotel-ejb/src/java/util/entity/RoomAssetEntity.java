/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author vincentyeo
 */
@Entity
public class RoomAssetEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mAssetId;
    private int cottonBud;
    private int dentalKit;
    private int showerCap;
    private int conditioner;
    private int soap;
    private int shavingKit;
    private int faceTowel;
    private int sewingKit;
    private int handTowel;
    private int bathTowel;
    private int bathMat;
    private int bathrobe;
    private int tissueBox;
    private int toiletRoll;
    private int slipper;
    private int bedsheet;
    private int blanketSheet;
    private int tea;
    private int coffee;
    private int sugar;
    private int coffeeCup;
    private int glassCup;
    private int tablespoon;
    private int electricKettle;
    private int water;
    private int coke;
    private int sprite;
    private int beer;
    private int greentea;
    private int laysClassic;
    private int dishwashingLiquid;
    private int laundryPowder;
    private int notebook;
    private int pen;

    @OneToOne
    private RoomTypeEntity roomType;
    
    
    public Long getmAssetId() {
        return mAssetId;
    }

    public void setmAssetId(Long mAssetId) {
        this.mAssetId = mAssetId;
    }

    public int getCottonBud() {
        return cottonBud;
    }

    public void setCottonBud(int cottonBud) {
        this.cottonBud = cottonBud;
    }

    public int getDentalKit() {
        return dentalKit;
    }

    public void setDentalKit(int dentalKit) {
        this.dentalKit = dentalKit;
    }

    public int getShowerCap() {
        return showerCap;
    }

    public void setShowerCap(int showerCap) {
        this.showerCap = showerCap;
    }

    public int getConditioner() {
        return conditioner;
    }

    public void setConditioner(int conditioner) {
        this.conditioner = conditioner;
    }

    public int getSoap() {
        return soap;
    }

    public void setSoap(int soap) {
        this.soap = soap;
    }

    public int getShavingKit() {
        return shavingKit;
    }

    public void setShavingKit(int shavingKit) {
        this.shavingKit = shavingKit;
    }

    public int getFaceTowel() {
        return faceTowel;
    }

    public void setFaceTowel(int faceTowel) {
        this.faceTowel = faceTowel;
    }

    public int getSewingKit() {
        return sewingKit;
    }

    public void setSewingKit(int sewingKit) {
        this.sewingKit = sewingKit;
    }

    public int getHandTowel() {
        return handTowel;
    }

    public void setHandTowel(int handTowel) {
        this.handTowel = handTowel;
    }

    public int getBathTowel() {
        return bathTowel;
    }

    public void setBathTowel(int bathTowel) {
        this.bathTowel = bathTowel;
    }

    public int getBathMat() {
        return bathMat;
    }

    public void setBathMat(int bathMat) {
        this.bathMat = bathMat;
    }

    public int getBathrobe() {
        return bathrobe;
    }

    public void setBathrobe(int bathrobe) {
        this.bathrobe = bathrobe;
    }

    public int getTissueBox() {
        return tissueBox;
    }

    public void setTissueBox(int tissueBox) {
        this.tissueBox = tissueBox;
    }

    public int getToiletRoll() {
        return toiletRoll;
    }

    public void setToiletRoll(int toiletRoll) {
        this.toiletRoll = toiletRoll;
    }

    public int getSlipper() {
        return slipper;
    }

    public void setSlipper(int slipper) {
        this.slipper = slipper;
    }

    public int getBedsheet() {
        return bedsheet;
    }

    public void setBedsheet(int bedsheet) {
        this.bedsheet = bedsheet;
    }

    public int getBlanketSheet() {
        return blanketSheet;
    }

    public void setBlanketSheet(int blanketSheet) {
        this.blanketSheet = blanketSheet;
    }

    public int getTea() {
        return tea;
    }

    public void setTea(int tea) {
        this.tea = tea;
    }

    public int getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getCoffeeCup() {
        return coffeeCup;
    }

    public void setCoffeeCup(int coffeeCup) {
        this.coffeeCup = coffeeCup;
    }

    public int getGlassCup() {
        return glassCup;
    }

    public void setGlassCup(int glassCup) {
        this.glassCup = glassCup;
    }

    public int getTablespoon() {
        return tablespoon;
    }

    public void setTablespoon(int tablespoon) {
        this.tablespoon = tablespoon;
    }

    public int getElectricKettle() {
        return electricKettle;
    }

    public void setElectricKettle(int electricKettle) {
        this.electricKettle = electricKettle;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getCoke() {
        return coke;
    }

    public void setCoke(int coke) {
        this.coke = coke;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public int getBeer() {
        return beer;
    }

    public void setBeer(int beer) {
        this.beer = beer;
    }

    public int getGreentea() {
        return greentea;
    }

    public void setGreentea(int greentea) {
        this.greentea = greentea;
    }

    public int getLaysClassic() {
        return laysClassic;
    }

    public void setLaysClassic(int laysClassic) {
        this.laysClassic = laysClassic;
    }

    public int getDishwashingLiquid() {
        return dishwashingLiquid;
    }

    public void setDishwashingLiquid(int dishwashingLiquid) {
        this.dishwashingLiquid = dishwashingLiquid;
    }

    public int getLaundryPowder() {
        return laundryPowder;
    }

    public void setLaundryPowder(int laundryPowder) {
        this.laundryPowder = laundryPowder;
    }

    public int getNotebook() {
        return notebook;
    }

    public void setNotebook(int notebook) {
        this.notebook = notebook;
    }

    public int getPen() {
        return pen;
    }

    public void setPen(int pen) {
        this.pen = pen;
    }
    
    public RoomTypeEntity getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeEntity roomType) {
        this.roomType = roomType;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mAssetId != null ? mAssetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the mAssetId fields are not set
        if (!(object instanceof RoomAssetEntity)) {
            return false;
        }
        RoomAssetEntity other = (RoomAssetEntity) object;
        if ((this.mAssetId == null && other.mAssetId != null) || (this.mAssetId != null && !this.mAssetId.equals(other.mAssetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "util.entity.RoomAssetEntity[ id=" + mAssetId + " ]";
    }


    
}
