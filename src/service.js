const DataReader = require('./data-reader');

class Service {

  constructor(dirPathToStocks,
              dirPathToSingleIndex) {
    this._dirPathToStocks = dirPathToStocks;
    this._dirPathToSingleIndex = dirPathToSingleIndex;
    this._dataReader = new DataReader();
  }

  calculate() {
    const stocks = _dataReader.readFiles(this._dirPathToStocks);
    const returns = calculateReturnsPreStock(stocks);
    const stats = calculateStats(stocks, returns);
  }

  calculateSingleIndexStat() {
    const index = this._dataReader.readFile(this._dirPathToSingleIndex);
    // const returns = calculateReturns(index);
    // const stats = calculateStat(index, returns);
    // return stats;
  }
}

new Service(
  '',
  '/Users/barakm/Projects/Portfolio-Calculator/resources/single-index',
)
  .calculateSingleIndexStat();