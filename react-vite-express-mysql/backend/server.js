const express = require('express');
const mysql2 = require('mysql2');
const cors = require('cors');
const app = express();
app.use(express.json()); // Middleware to parse JSON bodies

app.use(cors());
const db = mysql2.createConnection(
    {
        host: 'localhost',
        user: 'root',
        password: '!Mysqlazedesalom!#ab@',
        database: 'classicmodels'
    }
);

db.connect(err => {
    if (err) {
        return console.error('error connecting: ' + err.stack);
    }
    console.log('connected as id ' + db.threadId);
});


 app.get('/', (req, res) => {
    return res.json('Backend');
});

app.get('/customers', (req, res) => {
    db.query('SELECT * FROM customers', (err, results, fields) => {
        if (err) {
            res.status(500).json({ error: 'Internal server error' });
            return;
        }
        res.json(results);
    });
});

app.get('/customers/:customerNumber', (req, res) => {
    // Extract the id from the request parameters
    const { customerNumber } = req.params;
    console.log("customerNumber ", req.params)

    // SQL query to select the customer by id
    const sql = 'SELECT * FROM customers WHERE customerNumber = ?';

    // Execute the query
    db.query(sql, [customerNumber], (err, results) => {
        if (err) {
            console.error('Error fetching customer:', err);
            res.status(500).json({ error: 'Internal server error' });
            return;
        }

        // Check if the customer was found
        if (results.length > 0) {
            res.json(results[0]);
        } else {
            res.status(404).json({ error: 'Customer not found' });
        }
    });
});



app.listen(8081, () => {
    console.log('Listening');
});
