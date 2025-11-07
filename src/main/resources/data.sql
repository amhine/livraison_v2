-- ===============================
-- 🚗 VEHICLES
-- ===============================
INSERT INTO vehicle (type, max_weight_kg, max_volumem3, max_deliveries) VALUES
                                                                            ('BIKE', 50, 0.5, 10),
                                                                            ('VAN', 500, 5.0, 50),
                                                                            ('TRUCK', 5000, 30.0, 200);

-- ===============================
-- 🏢 WAREHOUSES
-- ===============================
INSERT INTO warehouse (name, address, latitude, longitude, heure_ouverture, heure_fermeture) VALUES
                                                                                                 ('Depot Central', '1 Rue Centrale, Casablanca', 33.5731, -7.5898, '08:00:00', '18:00:00'),
                                                                                                 ('Depot Marrakech', '10 Avenue Mohammed V, Marrakech', 31.6295, -7.9811, '08:00:00', '18:00:00');

-- ===============================
-- 🚚 TOURS
-- ===============================
INSERT INTO tour (date, distance_totale, optimizer_used, vehicle_id, warehouse_id) VALUES
                                                                                       ('2025-11-05', 120.5, 'plus_proche_voisin', 2, 1),
                                                                                       ('2025-11-06', 75.0, 'clarke_et_wright', 1, 2);

-- ===============================
-- 👤 CUSTOMERS
-- ===============================
INSERT INTO customer (name, address, latitude, longitude, poids, volume, preferred_time_slot, status, tour_id) VALUES
                                                                                                                   ('Alice Martin', '12 Rue des Fleurs, Casablanca', 33.5785, -7.6200, 2.5, 0.02, '08:00-09:00', 'PENDING', 1),
                                                                                                                   ('Bob Dupont', '5 Boulevard Voltaire, Casablanca', 33.5900, -7.6209, 7.0, 0.05, '09:00-10:00', 'IN_TRANSIT', 1),
                                                                                                                   ('Carla Leroy', '3 Rue Victor Hugo, Casablanca', 33.5860, -7.6205, 15.0, 0.10, '10:00-11:00', 'DELIVERED', 1),
                                                                                                                   ('Denis Morel', '20 Rue de la Republique, Marrakech', 31.6340, -7.9999, 3.0, 0.03, '08:00-09:00', 'PENDING', 2),
                                                                                                                   ('Emma Blanc', '8 Quai Saint-Antoine, Marrakech', 31.6300, -7.9800, 1.2, 0.01, '09:00-10:00', 'IN_TRANSIT', 2);

-- ===============================
-- 📦 DELIVERIES
-- ===============================
INSERT INTO delivery (poids, volume, status, planned_time, actual_time, tour_id, customer_id) VALUES
                                                                                                  (2.5, 0.02, 'PENDING', '08:30:00', NULL, 1, 1),
                                                                                                  (7.0, 0.05, 'IN_TRANSIT', '09:15:00', '09:40:00', 1, 2),
                                                                                                  (15.0, 0.10, 'DELIVERED', '10:00:00', '10:05:00', 1, 3),
                                                                                                  (3.0, 0.03, 'PENDING', '08:30:00', NULL, 2, 4),
                                                                                                  (1.2, 0.01, 'IN_TRANSIT', '09:00:00', NULL, 2, 5);

-- ===============================
-- 📜 DELIVERY HISTORY
-- ===============================
INSERT INTO delivery_history (delivery_id, customer_id, tour_id, delivery_date, planned_time, actual_time, delay_minutes, day_of_week, customer_name, customer_address, latitude, longitude, preferred_time_slot)
VALUES
    (1, 1, 1, '2025-11-05', '08:30:00', '08:35:00', 5, 'WEDNESDAY', 'Alice Martin', '12 Rue des Fleurs, Casablanca', 33.5785, -7.6200, '08:00-09:00'),
    (2, 2, 1, '2025-11-05', '09:15:00', '09:40:00', 25, 'WEDNESDAY', 'Bob Dupont', '5 Boulevard Voltaire, Casablanca', 33.5900, -7.6209, '09:00-10:00'),
    (3, 3, 1, '2025-11-05', '10:00:00', '10:05:00', 5, 'WEDNESDAY', 'Carla Leroy', '3 Rue Victor Hugo, Casablanca', 33.5860, -7.6205, '10:00-11:00');
