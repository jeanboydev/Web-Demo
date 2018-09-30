package com.jeanboy.web.demo.base;

import java.io.Serializable;
import java.util.List;

public interface BaseContract<PK extends Serializable, T> {

    /**
     * 延迟加载，不执行 SQL 语句，只是获取到对象的 id；
     * 当使用这个对象时，才从数据库中查询。
     *
     * @param id
     * @return
     */
    T load(PK id);

    /**
     * 直接执行 SQL 语句查询对象。
     *
     * @param id
     * @return
     */
    T get(PK id);

    /**
     * 直接执行 SQL 语句查询所有对象。
     *
     * @return
     */
    List<T> getAll();

    /**
     * 立即执行 SQL 语句 insert into 返回一个主键值；
     * 如果有关联实体，提交事务或者调用 flush() 方法，才会将关联对象也写入数据库。
     *
     * @param entity
     * @return
     */
    PK save(T entity);

    /**
     * 将实体对象添加到持久化上下文中，实体后续改变也会被记录；
     * 提交事务或者调用 flush() 方法，实体对象后续改变也会被保存到数据库中；
     * 必须在事务内执行，才能够将数据插入数据库。如果不在事务范围内执行，数据将丢失；
     * 返回值为 void。
     *
     * @param entity
     */
    void persist(T entity);

    /**
     * 会执行插入或者更新操作。如果该对象在数据库中已经存在则更新，不存在则插入；
     * 不在事务内执行，必须手动调用 flush()，才会将对象也写入数据库。
     *
     * @param entity
     */
    void saveOrUpdate(T entity);

    /**
     * 将实体对象添加到持久化上下文中，实体后续改变也会被记录；
     * 提交事务时，实体对象后续改变也会被保存到数据库中。
     *
     * @param entity
     */
    void update(T entity);

    /**
     * 更新数据库中的记录，将传递进来的实体创建一个副本添加到持久化上下文，并将副本返回；
     * 持久化上下文追踪副本实体改变，而传递进来的实体不在被追踪。
     *
     * @param entity
     */
    T merge(T entity);

    /**
     * 向数据库发送一系列 SQL 语句，并执行这些 SQL 语句，但不会向数据库提交；
     * 而 commit 方法则会首先调用 flush()，然后提交事务。
     */
    void flush();

    void delete(PK id);
}
