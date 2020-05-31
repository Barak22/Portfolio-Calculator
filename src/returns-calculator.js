const { e, log, round } = require('mathjs');

module.exports = class ReturnsCalculator {
  calculateReturns(indexesRawData) {
    const acc = [];

    return indexesRawData.map(indexRawData => {
      const stockReturns = this._calcRec(indexRawData.stockData, acc);
      return {
        stockName: indexRawData.stockName,
        stockReturns
      }
    })
  }

  _calcRec(indexRawData, acc) {
    if (indexRawData.length <= 1) return acc;
    else {
      const r = log(indexRawData[0].adj / indexRawData[1].adj, e);
      const roundedEr = round(r * 100, 2);
      const date = indexRawData[0].date;
      const newAcc = [...acc, { date, r: roundedEr }];
      return this._calcRec(indexRawData.slice(1), newAcc)
    }
  }
};
