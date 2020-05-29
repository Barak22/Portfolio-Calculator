const ReturnsCalculator = require('../src/returns-calculator');
const {expect} = require('chai');

describe('ReturnsCalculator', () => {
  const calculator = new ReturnsCalculator();

    it('should return [ 0 ] when calculating the same number', () => {
      const actual = calculator.calc([
        {
          date: 2,
          adj: '1'
        },
        {
          date: 1,
          adj: '1'
        }
      ]);

      expect(actual).to.deep.equal([0]);
    });

  it('should return [ 0.03 ] when calculating [ 303.070007/302.970001 ]', () => {
    const actual = calculator.calc([
      {
        date: 2,
        adj: '303.070007'
      },
      {
        date: 1,
        adj: '302.970001'
      }
    ]);

    expect(actual).to.deep.equal([0.03]);
  });

  it('should return [ 0.03, 4.21 ] when calculating [ 303.070007/302.970001, 302.970001/290.480011 ]', () => {
    const actual = calculator.calc([
      {
        date: 3,
        adj: '303.070007'
      },
      {
        date: 2,
        adj: '302.970001'
      },
      {
        date: 1,
        adj: '290.480011'
      }
    ]);

    expect(actual).to.deep.equal([0.03, 4.21]);
  });
});
