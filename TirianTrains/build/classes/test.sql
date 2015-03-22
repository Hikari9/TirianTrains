SELECT
    CAST(CONCAT(trip.year, '-', trip.month, '-', trip.day) AS DATE) 'Date' 
    -- (trip.departure_hour * 100 + trip.departure_minute) 'Departure Time',
    -- (trip.arrival_hour * 100 + trip.arrival_minute) 'Arrival Time',
    -- SUM(route.travel_cost) 'Cost',
    -- train.train_id 'Train ID', 
    -- model.name 'Train Model',
    FROM trip_assignment assignment
    INNER JOIN trip_schedule trip ON assignment.schedule_id = trip.schedule_id
    -- INNER JOIN ticket ON assignment.ticket_number = ticket.ticket_number
    -- INNER JOIN route ON trip.route_id = route.route_id
    -- INNER JOIN train ON train.train_id = trip.train_id
    -- INNER JOIN train_model model ON model.train_model_code = train.train_model_code;