const DataReader = require('./data-reader');
const ReturnsCalculator = require('./returns-calculator');

class Service {

  constructor(dirPathToStocks,
              dirPathToSingleIndex) {
    this._dirPathToStocks = dirPathToStocks;
    this._dirPathToSingleIndex = dirPathToSingleIndex;
    this._dataReader = new DataReader();
    this._returnsCalculator = new ReturnsCalculator();
  }

  calculate() {
    const stocks = _dataReader.readFiles(this._dirPathToStocks);
    const returns = calculateReturnsPreStock(stocks);
    const stats = calculateStats(stocks, returns);
  }

  async calculateMarketPortfolio() {
    const indexesRawData = await this._dataReader.readFiles(this._dirPathToSingleIndex);
    const returns = this._returnsCalculator.calculateReturns(indexesRawData);
    console.log(JSON.stringify(returns, undefined, 2));
    // const stats = calculateStat(index, returns);
    // return stats;
  }
}

new Service(
  '',
  '/Users/barakm/Projects/Portfolio-Calculator/resources/market-portfolio',
).calculateMarketPortfolio();