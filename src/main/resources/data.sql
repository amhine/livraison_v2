-- Vehicles
INSERT INTO vehicle (type, capacite_poids, capacite_volume, livraisons_max) VALUES
  ('velo', 50, 0.5, 10),
  ('camionnette', 500, 5.0, 50),
  ('camion', 5000, 30.0, 200);

-- Warehouses
INSERT INTO warehouses (name, address, latitude, longitude, heure_ouverture, heure_fermeture) VALUES
  ('Depot Central', '1 Rue Centrale, Paris', 48.8566, 2.3522, '08:00:00', '18:00:00'),
  ('Depot Lyon', '10 Avenue Lumiere, Lyon', 45.7640, 4.8357, '08:00:00', '18:00:00');

-- Tours (references vehicle_id, warehouse_id)
INSERT INTO tour (date, distance_totale, optimizer_used, vehicle_id, warehouse_id) VALUES
  ('2025-10-31', 120.5, 'plus_proche_voisin', 2, 1),
  ('2025-11-01', 75.0, 'clarke_et_wright', 1, 2);

-- Deliveries (references tour_id)
INSERT INTO delivery (name_client, address, latitude, longitude, poids, volume, status, tour_id, delivery_order) VALUES
  ('Alice Martin', '12 Rue des Fleurs, Paris', 48.8606, 2.3376, 2.5, 0.02, 'en_attente', 1, 0),
  ('Bob Dupont', '5 Boulevard Voltaire, Paris', 48.8570, 2.3709, 7.0, 0.05, 'en_cours', 1, 1),
  ('Carla Leroy', '3 Rue Victor Hugo, Paris', 48.8530, 2.3499, 15.0, 0.10, 'livree', 1, 2),
  ('Denis Morel', '20 Rue de la Republique, Lyon', 45.7630, 4.8350, 3.0, 0.03, 'en_attente', 2, 0),
  ('Emma Blanc', '8 Quai Saint-Antoine, Lyon', 45.7645, 4.8350, 1.2, 0.01, 'en_cours', 2, 1);
