CREATE TABLE IF NOT EXISTS `short_url` (
    `id` VARCHAR(255) PRIMARY KEY,
    `original_url` VARCHAR(2048) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `last_accessed_at` TIMESTAMP,
    `expiry_at` TIMESTAMP,
    `is_active` BOOLEAN DEFAULT TRUE
);