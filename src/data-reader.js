const csv = require('csv-parser');
const fs = require('fs');

module.exports = class DataReader {
  async readFile(path) {
    const results = await new Promise(resolve => {
      const results = [];
      const singleIndexFileName = fs.readdirSync(path)[0];
      fs.createReadStream(path + '/' + singleIndexFileName)
        .pipe(csv({
          'skipLines': 1,
          'headers': ['Date', 'Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']
        }))
        .on('data', data => results.push(data))
        .on('end', () => resolve(results))
    });

    return results
      .map(this._toDateAndAdjOnly())
      .sort(this._byDate());
  }

  _toDateAndAdjOnly() {
    return data => ({
      'date': data.Date,
      'adj': data['Adj Close']

    });
  }

  _byDate() {
    return (data1, data2) => {
      if (data1.date < data2.date) return 1;
      else return -1;
    };
  }
};