SELECT
    CAST(CONCAT(t.year, '-', t.month, '-', t.day) AS DATE)
    FROM trip_schedule t;