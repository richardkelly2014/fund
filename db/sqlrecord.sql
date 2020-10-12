CREATE TABLE "stock_daily" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
"ts_code" TEXT NOT NULL,
"symbol" TEXT NOT NULL,
"trade_date" TEXT NOT NULL,
"open" integer(12,4) NOT NULL,
"high" integer(12,4) NOT NULL,
"low" integer(12,4) NOT NULL,
"close" integer(12,4) NOT NULL,
"pre_close" integer(12,4) NOT NULL,
"change" integer(12,4) NOT NULL,
"pct_chg" integer(12,4) NOT NULL,
"vol" integer(12,4) NOT NULL,
"amount" integer(12,4) NOT NULL,
"year" integer NOT NULL DEFAULT 0,
"month" integer NOT NULL DEFAULT 0,
"day" integer NOT NULL DEFAULT 0,
"week" integer NOT NULL DEFAULT 0
);

CREATE TABLE "stock_daily_analysis" (
  "id" INTEGER NOT NULL,
"ts_code" TEXT NOT NULL,
"symbol" TEXT NOT NULL,
"trade_date" TEXT NOT NULL,
"change_type" integer NOT NULL,
"change_stop" integer NOT NULL,
"change_stop_num" integer NOT NULL,
"low_day" integer NOT NULL,
"high_day" integer NOT NULL,
PRIMARY KEY("id")
)