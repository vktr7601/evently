# Payment Service

## Mock Payment Gateway - Test Cards

The mock payment gateway simulates third-party payment processing for testing purposes. Use the test cards below to trigger specific scenarios.

### API Endpoint

```
POST /payments/process
```

### Request Body

```json
{
  "order_id": 1,
  "amount": 100.00,
  "card_number": "4242424242424242",
  "card_expiry": "12/28",
  "card_cvv": "123"
}
```

### Test Cards

| Scenario             | Card Number        | Card Expiry | Card CVV | Expected Result       |
|----------------------|--------------------|-------------|----------|-----------------------|
| Successful payment   | 4242424242424242   | 12/28       | 123      | Payment approved      |
| Card declined        | 4242424242420000   | 12/28       | 123      | Card declined         |
| Insufficient funds   | 4242424242421111   | 12/28       | 123      | Insufficient funds    |
| Expired card         | 4242424242424242   | 01/20       | 123      | Card expired          |
| Invalid card number  | 123                | 12/28       | 123      | Invalid card number   |
| Invalid amount       | 4242424242424242   | 12/28       | 123      | Invalid amount        |
| Null/blank expiry    | 4242424242424242   |             | 123      | Card expired          |
| Invalid expiry format| 4242424242424242   | 13/28       | 123      | Card expired          |

### Test Card Rules

- **Card ending in `0000`** - Always returns "Card declined"
- **Card ending in `1111`** - Always returns "Insufficient funds"
- **Card number shorter than 13 digits** - Returns "Invalid card number"
- **Expired date (past `MM/yy`)** - Returns "Card expired"
- **Null, blank, or invalid expiry format** - Returns "Card expired"
- **Amount <= 0 or null** - Returns "Invalid amount"
- **Any other valid card** - Returns "Payment approved" with a generated transaction ID (`txn_...`)

### Notes

- Card expiry must be in `MM/yy` format (e.g., `12/28`)
- For the "Invalid amount" scenario, send `amount` as `0` or a negative number
- Successful payments return a transaction ID in the format `txn_<16 hex chars>`
