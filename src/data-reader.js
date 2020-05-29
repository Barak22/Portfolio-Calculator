const csv = require('csv-parser');
const fs = require('fs');

module.exports = class DataReader {
  async readFile(path) {
    const results = await new Promise(resolve => {
      const results = [];
      const singleIndexFileName = fs.readdirSync(path)[0];
      fs.createReadStream(path + '/' + singleIndexFileName)
        .pipe(csv(['Date', 'Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']))
        .on('data', data => {
          results.push(data)
        })
        .on('end', () => resolve(results))
    });

    return results.map(data => ({
      'date': data.Date,
      'adj': data['Adj Close']
    }));
  }
};