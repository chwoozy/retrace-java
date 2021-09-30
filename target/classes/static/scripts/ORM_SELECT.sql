SELECT
    id,
    order_date,
    employee_id,
    customer_id,
    shipping_fee,
    taxes,
    payment_type
FROM orders
WHERE customer_id = {}
    LIMIT 1;