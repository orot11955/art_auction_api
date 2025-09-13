-- Create databases for each service
CREATE DATABASE IF NOT EXISTS auth_db;
CREATE DATABASE IF NOT EXISTS artwork_db;
CREATE DATABASE IF NOT EXISTS auction_db;
CREATE DATABASE IF NOT EXISTS bid_db;
CREATE DATABASE IF NOT EXISTS settlement_db;
CREATE DATABASE IF NOT EXISTS notification_db;

-- Grant permissions
GRANT ALL PRIVILEGES ON auth_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON artwork_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON auction_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON bid_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON settlement_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON notification_db.* TO 'root'@'%';

FLUSH PRIVILEGES;
