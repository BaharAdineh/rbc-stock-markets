CREATE SCHEMA rbc;

CREATE TABLE rbc.stock (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            client_id VARCHAR(255) NOT NULL,
                            quarter INT NOT NULL,
                            stock VARCHAR(255) NOT NULL,
                            date DATE NOT NULL,
                            open DOUBLE NOT NULL,
                            high DOUBLE NOT NULL,
                            low DOUBLE NOT NULL,
                            close DOUBLE NOT NULL,
                            volume INT NOT NULL,
                            percent_change_price DOUBLE NOT NULL,
                            percent_change_volume_over_last_week DOUBLE NOT NULL,
                            previous_weeks_volume INT NOT NULL,
                            next_weeks_open DOUBLE NOT NULL,
                            next_weeks_close DOUBLE NOT NULL,
                            percent_change_next_weeks_price DOUBLE NOT NULL,
                            days_to_next_dividend INT NOT NULL,
                            percent_return_next_dividend DOUBLE NOT NULL
);