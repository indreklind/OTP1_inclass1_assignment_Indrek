
PRAGMA foreign_keys = ON;

-- Language-independent units
CREATE TABLE IF NOT EXISTS unit (
                                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    unit_key TEXT NOT NULL UNIQUE,
                                    category TEXT NOT NULL,
                                    symbol TEXT NOT NULL
);

-- One translation per unit and language
CREATE TABLE IF NOT EXISTS unit_translation (
                                                unit_id INTEGER NOT NULL,
                                                lang TEXT NOT NULL,
                                                name TEXT NOT NULL,
                                                description TEXT NOT NULL,
                                                PRIMARY KEY (unit_id, lang),
    FOREIGN KEY (unit_id) REFERENCES unit(id) ON DELETE CASCADE
    );

-- Travel calculations
CREATE TABLE IF NOT EXISTS travel_record (
                                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                                             distance REAL NOT NULL,
                                             speed REAL NOT NULL,
                                             time REAL NOT NULL,
                                             distance_unit_id INTEGER NOT NULL,
                                             speed_unit_id INTEGER NOT NULL,
                                             time_unit_id INTEGER NOT NULL,
                                             created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                             FOREIGN KEY (distance_unit_id) REFERENCES unit(id),
    FOREIGN KEY (speed_unit_id) REFERENCES unit(id),
    FOREIGN KEY (time_unit_id) REFERENCES unit(id)
    );

-- Temperature conversion history
CREATE TABLE IF NOT EXISTS temperature_conversion (
                                                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                                                      value REAL NOT NULL,
                                                      from_unit_id INTEGER NOT NULL,
                                                      to_unit_id INTEGER NOT NULL,
                                                      result REAL NOT NULL,
                                                      lang TEXT NOT NULL,
                                                      created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                      FOREIGN KEY (from_unit_id) REFERENCES unit(id),
    FOREIGN KEY (to_unit_id) REFERENCES unit(id)
    );

-- Unit lookup data
INSERT OR IGNORE INTO unit (unit_key, category, symbol) VALUES
('celsius', 'temperature', '°C'),
('fahrenheit', 'temperature', '°F'),
('kelvin', 'temperature', 'K'),
('kilometer', 'distance', 'km'),
('mile', 'distance', 'mi'),
('km_per_hour', 'speed', 'km/h'),
('mile_per_hour', 'speed', 'mph'),
('hour', 'time', 'h'),
('minute', 'time', 'min');

-- English translations
INSERT OR IGNORE INTO unit_translation
SELECT id, 'en',
       CASE unit_key
           WHEN 'celsius' THEN 'Celsius'
           WHEN 'fahrenheit' THEN 'Fahrenheit'
           WHEN 'kelvin' THEN 'Kelvin'
           WHEN 'kilometer' THEN 'Kilometer'
           WHEN 'mile' THEN 'Mile'
           WHEN 'km_per_hour' THEN 'Kilometres per hour'
           WHEN 'mile_per_hour' THEN 'Miles per hour'
           WHEN 'hour' THEN 'Hour'
           WHEN 'minute' THEN 'Minute' END,
       CASE unit_key
           WHEN 'celsius' THEN 'Temperature in degrees Celsius'
           WHEN 'fahrenheit' THEN 'Temperature in degrees Fahrenheit'
           WHEN 'kelvin' THEN 'Absolute temperature in kelvin'
           WHEN 'kilometer' THEN 'Metric distance unit'
           WHEN 'mile' THEN 'Imperial distance unit'
           WHEN 'km_per_hour' THEN 'Speed in kilometres per hour'
           WHEN 'mile_per_hour' THEN 'Speed in miles per hour'
           WHEN 'hour' THEN 'Time measured in hours'
           WHEN 'minute' THEN 'Time measured in minutes' END
FROM unit;

-- Finnish translations
INSERT OR IGNORE INTO unit_translation
SELECT id, 'fi',
       CASE unit_key
           WHEN 'celsius' THEN 'Celsius'
           WHEN 'fahrenheit' THEN 'Fahrenheit'
           WHEN 'kelvin' THEN 'Kelvin'
           WHEN 'kilometer' THEN 'Kilometri'
           WHEN 'mile' THEN 'Maili'
           WHEN 'km_per_hour' THEN 'Kilometriä tunnissa'
           WHEN 'mile_per_hour' THEN 'Mailia tunnissa'
           WHEN 'hour' THEN 'Tunti'
           WHEN 'minute' THEN 'Minuutti' END,
       CASE unit_key
           WHEN 'celsius' THEN 'Lämpötila Celsius-asteina'
           WHEN 'fahrenheit' THEN 'Lämpötila Fahrenheit-asteina'
           WHEN 'kelvin' THEN 'Absoluuttinen lämpötila kelvineinä'
           WHEN 'kilometer' THEN 'Metrinen etäisyysyksikkö'
           WHEN 'mile' THEN 'Brittiläinen etäisyysyksikkö'
           WHEN 'km_per_hour' THEN 'Nopeus kilometreinä tunnissa'
           WHEN 'mile_per_hour' THEN 'Nopeus maileina tunnissa'
           WHEN 'hour' THEN 'Aika tunteina'
           WHEN 'minute' THEN 'Aika minuutteina' END
FROM unit;

-- Swedish translations
INSERT OR IGNORE INTO unit_translation
SELECT id, 'sv',
       CASE unit_key
           WHEN 'celsius' THEN 'Celsius'
           WHEN 'fahrenheit' THEN 'Fahrenheit'
           WHEN 'kelvin' THEN 'Kelvin'
           WHEN 'kilometer' THEN 'Kilometer'
           WHEN 'mile' THEN 'Engelsk mil'
           WHEN 'km_per_hour' THEN 'Kilometer per timme'
           WHEN 'mile_per_hour' THEN 'Engelska mil per timme'
           WHEN 'hour' THEN 'Timme'
           WHEN 'minute' THEN 'Minut' END,
       CASE unit_key
           WHEN 'celsius' THEN 'Temperatur i grader Celsius'
           WHEN 'fahrenheit' THEN 'Temperatur i grader Fahrenheit'
           WHEN 'kelvin' THEN 'Absolut temperatur i kelvin'
           WHEN 'kilometer' THEN 'Metrisk avståndsenhet'
           WHEN 'mile' THEN 'Brittisk avståndsenhet'
           WHEN 'km_per_hour' THEN 'Hastighet i kilometer per timme'
           WHEN 'mile_per_hour' THEN 'Hastighet i engelska mil per timme'
           WHEN 'hour' THEN 'Tid mätt i timmar'
           WHEN 'minute' THEN 'Tid mätt i minuter' END
FROM unit;

-- Japanese translations
INSERT OR IGNORE INTO unit_translation
SELECT id, 'ja',
       CASE unit_key
           WHEN 'celsius' THEN '摂氏'
           WHEN 'fahrenheit' THEN '華氏'
           WHEN 'kelvin' THEN 'ケルビン'
           WHEN 'kilometer' THEN 'キロメートル'
           WHEN 'mile' THEN 'マイル'
           WHEN 'km_per_hour' THEN '時速キロメートル'
           WHEN 'mile_per_hour' THEN '時速マイル'
           WHEN 'hour' THEN '時間'
           WHEN 'minute' THEN '分' END,
       CASE unit_key
           WHEN 'celsius' THEN '摂氏による温度'
           WHEN 'fahrenheit' THEN '華氏による温度'
           WHEN 'kelvin' THEN 'ケルビンによる絶対温度'
           WHEN 'kilometer' THEN 'メートル法の距離単位'
           WHEN 'mile' THEN 'ヤード・ポンド法の距離単位'
           WHEN 'km_per_hour' THEN '時速キロメートルで表す速度'
           WHEN 'mile_per_hour' THEN '時速マイルで表す速度'
           WHEN 'hour' THEN '時間単位'
           WHEN 'minute' THEN '分単位' END
FROM unit;

-- Arabic translations
INSERT OR IGNORE INTO unit_translation
SELECT id, 'ar',
       CASE unit_key
           WHEN 'celsius' THEN 'درجة مئوية'
           WHEN 'fahrenheit' THEN 'درجة فهرنهايت'
           WHEN 'kelvin' THEN 'كلفن'
           WHEN 'kilometer' THEN 'كيلومتر'
           WHEN 'mile' THEN 'ميل'
           WHEN 'km_per_hour' THEN 'كيلومتر في الساعة'
           WHEN 'mile_per_hour' THEN 'ميل في الساعة'
           WHEN 'hour' THEN 'ساعة'
           WHEN 'minute' THEN 'دقيقة' END,
       CASE unit_key
           WHEN 'celsius' THEN 'درجة الحرارة بالمئوية'
           WHEN 'fahrenheit' THEN 'درجة الحرارة بالفهرنهايت'
           WHEN 'kelvin' THEN 'درجة الحرارة المطلقة بالكلفن'
           WHEN 'kilometer' THEN 'وحدة مسافة مترية'
           WHEN 'mile' THEN 'وحدة مسافة إمبراطورية'
           WHEN 'km_per_hour' THEN 'السرعة بالكيلومتر في الساعة'
           WHEN 'mile_per_hour' THEN 'السرعة بالميل في الساعة'
           WHEN 'hour' THEN 'الوقت بالساعات'
           WHEN 'minute' THEN 'الوقت بالدقائق' END
FROM unit;
