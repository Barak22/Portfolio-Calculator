const csv = require('csv-parser');
const fs = require('fs');

module.exports = class DataReader {
  async readFiles(path) {
    const results = [];

    const singleIndexFilesNames = fs.readdirSync(path);

    await Promise.all(singleIndexFilesNames.map(singleIndexFileName => {
      return new Promise(resolve => {
        const stockData = [];
        fs.createReadStream(path + '/' + singleIndexFileName)
          .pipe(csv(this._initCSVOptions()))
          .on('data', data => stockData.push(data))
          .on('end', () => {
            const sortedStockData = stockData.map(this._toDateAndAdjOnly()).sort(this._byDate());
            results.push({ stockName: singleIndexFileName, stockData: sortedStockData });
            resolve();
          })
      })
    }));

    // console.log('**** ' + JSON.stringify(results, undefined, 2));

    return results
  }

  _initCSVOptions() {
    return {
      'skipLines': 1,
      'headers': ['Date', 'Open', 'High', 'Low', 'Close', 'Adj Close', 'Volume']
    };
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