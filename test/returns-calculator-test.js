const ReturnsCalculator = require('../src/returns-calculator');
const { expect } = require('chai');

describe('ReturnsCalculator', () => {
  const calculator = new ReturnsCalculator();

  it('should return [ 0 ] when calculating the same number', () => {
    const actual = calculator.calculateReturns([
      {
        date: '28/05/2020',
        adj: '1'
      },
      {
        date: '01/05/2020',
        adj: '1'
      }
    ]);

    expect(actual).to.deep.equal([{ date: '28/05/2020', r: 0 }]);
  });

  it('should return [ 0.03 ] when calculating [ 303.070007/302.970001 ]', () => {
    const actual = calculator.calculateReturns([
      {
        date: '28/05/2020',
        adj: '303.070007'
      },
      {
        date: '01/05/2020',
        adj: '302.970001'
      }
    ]);

    expect(actual).to.deep.equal([{ date: '28/05/2020', r: 0.03 }]);
  });

  it('should return [ 0.03, 4.21 ] when calculating [ 303.070007/302.970001, 302.970001/290.480011 ]', () => {
    const actual = calculator.calculateReturns([
      {
        date: '28/05/2020',
        adj: '303.070007'
      },
      {
        date: '01/05/2020',
        adj: '302.970001'
      },
      {
        date: '01/04/2020',
        adj: '290.480011'
      }
    ]);

    expect(actual).to.deep.equal([{ date: '28/05/2020', r: 0.03 }, { date: '01/05/2020', r: 4.21 }]);
  });
});
