package com.nixmash.jangles.enums;


public enum JanglesSqlDatatype {
	BIGINT,
	BIGSERIAL,
	BIT,
	BOOL,
	BPCHAR,
	CHAR,
	DATE,
	DATETIME,
	DECIMAL,
	DOUBLE,
	FLOAT,
	FLOAT4,
	FLOAT8,
	INT,
	INT4,
	INT8,
	LONGTEXT,
	NUMERIC,
	SERIAL,
	SMALLINT,
	TEXT,
	TIME,
	TIMESTAMP,
	VARCHAR,
	NA;
	
	public static JanglesSqlDatatype toSqlDataType(String Str)
	{
		try {return valueOf(Str);}
		catch (Exception ex){return NA;}
	}
}

