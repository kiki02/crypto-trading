# crypto-trading
crypto trading system

1. A 10 seconds interval scheduler to retrieve the pricing from the source, and then store the best pricing into the database
=> The class com.aquariux.crypto.scheduler.aggregation.PriceAggregationScheduler

2. Create an api to retrieve the latest best aggregated price.
=> 
Method: GET
Path: http://localhost:8080/api/aggregation/price-list

3. Create an api which allows users to trade based on the latest best aggregated
price.
=>
	Method: POST
	Path: http://localhost:8080/api/trading/create
	Request body: All fields are mandatory.
		{
			"requestId": "String",	// unique request ID
			"requestTime": number,	// the current time in milliseconds
			"tradingType": number,	// 0 is SELL, and 1 is BUY
			"tradingPair": "String",	// ETHUSDT or BTCUSDT
			"amount": number	// The trading crypto amount
		}
	Example:
	{
		"requestId": "a9006ed9-032e-42a5-920b-85f3b242349c",
		"requestTime": 1684041646666,
		"tradingType": 1,
		"tradingPair": "ETHUSDT",
		"amount": 0.25
	}

4. Create an api to retrieve the user’s crypto currencies wallet balance
=>
	Method: GET
	Path: http://localhost:8080/api/wallet/balance

5. Create an api to retrieve the user trading history
=>
	Method: GET
	Path: http://localhost:8080/api/trading/history
