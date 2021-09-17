FULL_JOIN = '''
SELECT
customers.id,
customers.first_name,
customers.last_name,
customers.email_address,
customers.mobile_phone,
orders.id AS order_id,
orders.shipping_fee,
orders.taxes
FROM customers
LEFT JOIN orders ON customers.id = orders.customer_id
UNION
SELECT
customers.id,
customers.first_name,
customers.last_name,
customers.email_address,
customers.mobile_phone,
orders.id AS order_id,
orders.shipping_fee,
orders.taxes
FROM customers
RIGHT JOIN orders ON customers.id = orders.customer_id
ORDER BY id;
'''

LEFT_JOIN = '''
SELECT
orders.id,
orders.order_date,
orders.shipped_date,
orders.shipping_fee,
orders.taxes,
orders.payment_type,
orders_status.id AS status_id,
orders_status.status_name
FROM orders
LEFT JOIN orders_status
ON orders.id = orders_status.id
ORDER BY order_date;
'''

RIGHT_JOIN = '''
SELECT
purchase_orders.id,
purchase_orders.supplier_id,
purchase_orders.created_by,
purchase_orders.creation_date,
purchase_orders.shipping_fee,
purchase_orders.taxes,
purchase_orders.payment_amount,
purchase_orders.payment_method,
purchase_order_status.id AS status_id,
purchase_order_status.status
FROM purchase_order_status
RIGHT JOIN purchase_orders
ON purchase_order_status.id = purchase_orders.status_id
ORDER BY creation_date;
'''

CREATE_TABLE = '''
CREATE TABLE customer_privileges (
        customer_id int,
        privilege_id int
);
'''

DROP_TABLE = '''
DROP TABLE IF EXISTS customer_privileges;
'''