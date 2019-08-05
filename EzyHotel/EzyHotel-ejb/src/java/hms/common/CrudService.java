/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hms.common;

import java.util.List;
/**
 *
 * @author berni
 * @param <T>
 */
public interface CrudService<T> {
    public T createEntity(T entity);
    public T retrieveEntityById(Long id);
    public List<T> retrieveAllEntities();
    public void deleteEntity(Long id);
    public void updateEntity(T entity);
    public T retrieveEntity(String attribute, String value);
    public List<T> retrieveEntityBy2Filters(String attributeA, String valueA, String attributeB, String valueB);
}
