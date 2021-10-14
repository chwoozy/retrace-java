package com.stackify.sandbox.model;

public enum CustomSQL {
    ORMSELECT("SELECT\n" +
            "    id,\n" +
            "    order_date,\n" +
            "    employee_id,\n" +
            "    customer_id,\n" +
            "    shipping_fee,\n" +
            "    taxes,\n" +
            "    payment_type\n" +
            "FROM orders\n" +
            "WHERE customer_id = %o\n" +
            "    LIMIT 1;"
    ),
    ORMGETROWS("SELECT\n" +
            "    id,\n" +
            "    first_name,\n" +
            "    last_name,\n" +
            "    city,\n" +
            "    country_region,\n" +
            "    mobile_phone\n" +
            "FROM customers;"
    ),
    ORMN1("SELECT\n" +
            "    cust_obj.id,\n" +
            "    cust_obj.first_name,\n" +
            "    cust_obj.last_name,\n" +
            "    cust_obj.email_address,\n" +
            "    cust_obj.city,\n" +
            "    cust_obj.country_region,\n" +
            "    cust_obj.mobile_phone,\n" +
            "    cust_obj.order_id,\n" +
            "    cust_obj.order_date,\n" +
            "    cust_obj.customer_id,\n" +
            "    cust_obj.shipping_fee,\n" +
            "    cust_obj.taxes\n" +
            "    FROM ( SELECT\n" +
            "        customers.id,\n" +
            "        customers.first_name,\n" +
            "        customers.last_name,\n" +
            "        customers.email_address,\n" +
            "        customers.city,\n" +
            "        customers.country_region,\n" +
            "        customers.mobile_phone,\n" +
            "        orders.id AS order_id,\n" +
            "        orders.order_date,\n" +
            "        orders.customer_id,\n" +
            "        orders.shipping_fee,\n" +
            "        orders.taxes,\n" +
            "        CASE \n" +
            "            WHEN (orders.id IS NULL) THEN CAST(NULL AS UNSIGNED) \n" +
            "            ELSE 1\n" +
            "        END AS parity\n" +
            "        FROM  customers\n" +
            "        LEFT OUTER JOIN orders ON customers.id = orders.customer_id\n" +
            "    )  AS cust_obj\n" +
            "    ORDER BY cust_obj.id, cust_obj.parity ASC;"
    ),
    FULLJOIN("SELECT\n" +
            "customers.id,\n" +
            "customers.first_name,\n" +
            "customers.last_name,\n" +
            "customers.email_address,\n" +
            "customers.mobile_phone,\n" +
            "orders.id AS order_id,\n" +
            "orders.shipping_fee,\n" +
            "orders.taxes\n" +
            "FROM customers\n" +
            "LEFT JOIN orders ON customers.id = orders.customer_id\n" +
            "UNION\n" +
            "SELECT\n" +
            "customers.id,\n" +
            "customers.first_name,\n" +
            "customers.last_name,\n" +
            "customers.email_address,\n" +
            "customers.mobile_phone,\n" +
            "orders.id AS order_id,\n" +
            "orders.shipping_fee,\n" +
            "orders.taxes\n" +
            "FROM customers\n" +
            "RIGHT JOIN orders ON customers.id = orders.customer_id\n" +
            "ORDER BY id;"
    ),
    LEFTJOIN("SELECT\n" +
            "orders.id,\n" +
            "orders.order_date,\n" +
            "orders.shipped_date,\n" +
            "orders.shipping_fee,\n" +
            "orders.taxes,\n" +
            "orders.payment_type,\n" +
            "orders_status.id AS status_id,\n" +
            "orders_status.status_name\n" +
            "FROM orders\n" +
            "LEFT JOIN orders_status\n" +
            "ON orders.id = orders_status.id\n" +
            "ORDER BY order_date;"
    ),
    RIGHTJOIN("SELECT\n" +
            "purchase_orders.id,\n" +
            "purchase_orders.supplier_id,\n" +
            "purchase_orders.created_by,\n" +
            "purchase_orders.creation_date,\n" +
            "purchase_orders.shipping_fee,\n" +
            "purchase_orders.taxes,\n" +
            "purchase_orders.payment_amount,\n" +
            "purchase_orders.payment_method,\n" +
            "purchase_order_status.id AS status_id,\n" +
            "purchase_order_status.status\n" +
            "FROM purchase_order_status\n" +
            "RIGHT JOIN purchase_orders\n" +
            "ON purchase_order_status.id = purchase_orders.status_id\n" +
            "ORDER BY creation_date;"
    ),
    CREATETABLE("CREATE TABLE customer_privileges (\n" +
            "        customer_id int,\n" +
            "        privilege_id int);"
    ),
    DROPTABLE("DROP TABLE IF EXISTS customer_privileges;");


    private String query;

    CustomSQL(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
