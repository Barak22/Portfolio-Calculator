const { e, log, round } = require('mathjs');

module.exports = class ReturnsCalculator {
  calc(indexRawData) {
    const acc = [];

    const s = this._calcRec(indexRawData, acc);
    console.log(s);
    return s
  }

  _calcRec(indexRawData, acc) {
    if (indexRawData.length <= 1) return acc;
    else {
      const Er = log(indexRawData[0].adj / indexRawData[1].adj, e);
      const roundedEr = round(Er, 4) * 100;
      const newAcc = [...acc, roundedEr];
      return this._calcRec(indexRawData.slice(1), newAcc)
    }
  }
};
