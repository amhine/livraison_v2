
INSERT INTO vehicle (type, capacite_poids, capacite_volume, livraisons_max) VALUES
   ('velo', 50, 0.5, 15),
   ('camionnette', 1000, 8.0, 50),
   ('camion', 5000, 40.0, 100);

INSERT INTO warehouses (name, address, latitude, longitude, heure_ouverture, heure_fermeture) VALUES
    ('Depot Central Paris', '1 Rue Centrale, Paris 75001', 48.8566, 2.3522, '06:00:00', '22:00:00'),
    ('Depot Lyon Confluence', '10 Avenue Lumiere, Lyon 69002', 45.7640, 4.8357, '06:00:00', '22:00:00'),
    ('Depot Marseille Vieux-Port', '5 Quai du Port, Marseille 13001', 43.2965, 5.3698, '06:00:00', '22:00:00');

INSERT INTO customer (name, address, latitude, longitude, preferred_time_slot) VALUES
    ('Alice Martin', '12 Rue des Fleurs, Paris 75010', 48.8606, 2.3376, '09:00-11:00'),
    ('Bob Dupont', '5 Boulevard Voltaire, Paris 75011', 48.8570, 2.3709, '14:00-16:00'),
    ('Carla Leroy', '3 Rue Victor Hugo, Paris 75016', 48.8530, 2.3499, '10:00-12:00'),
    ('Denis Morel', '20 Rue de la Republique, Lyon 69002', 45.7630, 4.8350, '13:00-15:00'),
    ('Emma Blanc', '8 Quai Saint-Antoine, Lyon 69002', 45.7645, 4.8350, '16:00-18:00'),
    ('François Garnier', '15 Avenue Foch, Lyon 69006', 45.7690, 4.8490, '09:00-11:00'),
    ('Sophie Bernard', '22 Cours Vitton, Lyon 69006', 45.7710, 4.8520, '11:00-13:00'),
    ('Lucas Petit', '30 Rue Paradis, Marseille 13006', 43.2920, 5.3780, '14:00-16:00'),
    ('Marie Dubois', '18 La Canebiere, Marseille 13001', 43.2950, 5.3750, '10:00-12:00'),
    ('Pierre Lambert', '7 Rue Saint-Ferreol, Marseille 13001', 43.2940, 5.3760, '15:00-17:00');

INSERT INTO tour (date, distance_totale, optimizer_used, status, vehicle_id, warehouse_id) VALUES
    ('2025-11-01', 0, 'plus_proche_voisin', 'PLANNED', 2, 1),
    ('2025-11-01', 0, 'clarke_et_wright', 'IN_PROGRESS', 1, 2),
    ('2025-11-02', 0, 'clarke_et_wright', 'COMPLETED', 3, 1),
    ('2025-11-03', 0, 'plus_proche_voisin', 'COMPLETED', 2, 3);

INSERT INTO delivery (name_client, address, latitude, longitude, poids, volume, status, tour_id, customer_id, delivery_order) VALUES
  ('Alice Martin', '12 Rue des Fleurs, Paris 75010', 48.8606, 2.3376, 2.5, 0.02, 'en_attente', 1, 1, 0),
  ('Bob Dupont', '5 Boulevard Voltaire, Paris 75011', 48.8570, 2.3709, 7.0, 0.05, 'en_attente', 1, 2, 1),
  ('Carla Leroy', '3 Rue Victor Hugo, Paris 75016', 48.8530, 2.3499, 15.0, 0.10, 'en_attente', 1, 3, 2),
   ('Denis Morel', '20 Rue de la Republique, Lyon 69002', 45.7630, 4.8350, 3.0, 0.03, 'en_cours', 2, 4, 0),
   ('Emma Blanc', '8 Quai Saint-Antoine, Lyon 69002', 45.7645, 4.8350, 1.2, 0.01, 'en_cours', 2, 5, 1),
    ('François Garnier', '15 Avenue Foch, Lyon 69006', 45.7690, 4.8490, 2.8, 0.02, 'en_attente', 2, 6, 2),
     ('Sophie Bernard', '22 Cours Vitton, Lyon 69006', 45.7710, 4.8520, 25.0, 0.15, 'livree', 3, 7, 0),
    ('Alice Martin', '12 Rue des Fleurs, Paris 75010', 48.8606, 2.3376, 18.5, 0.12, 'livree', 3, 1, 1),
     ('Bob Dupont', '5 Boulevard Voltaire, Paris 75011', 48.8570, 2.3709, 12.3, 0.08, 'livree', 3, 2, 2);
INSERT INTO delivery_history (customer_id, delivery_id, tour_id, delivery_date, planned_time, actual_time, delay, day_of_week) VALUES
    (7, 7, 3, '2025-11-02', '09:00:00', '09:15:00', 15, 'WEDNESDAY'),
    (1, 8, 3, '2025-11-02', '10:30:00', '10:45:00', 15, 'WEDNESDAY'),
    (2, 9, 3, '2025-11-02', '12:00:00', '12:10:00', 10, 'WEDNESDAY'),
    (8, 10, 4, '2025-11-03', '14:00:00', '14:05:00', 5, 'THURSDAY'),
    (9, 11, 4, '2025-11-03', '15:30:00', '15:40:00', 10, 'THURSDAY'),
    (10, 12, 4, '2025-11-03', '16:30:00', '16:25:00', -5, 'THURSDAY');