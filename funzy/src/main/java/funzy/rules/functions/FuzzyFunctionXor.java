// The MIT License - Copyright (c) 2007 Universitetet i Oslo

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE. 
package funzy.rules.functions;

import static com.google.common.collect.Lists.immutableList;

import java.util.List;

/**
 * Implementation of a Fuzzy XOR operator using AND, OR, NOT operators.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class FuzzyFunctionXor extends FuzzyFunctionMultiple {
    private FuzzyFunction min, max, minus;

    public FuzzyFunctionXor(FuzzyFunction and, FuzzyFunction or,
            FuzzyFunction not) {
        min = and;
        max = or;
        minus = not;
    }

    /* (non-Javadoc)
     * @see funzy.operators.MultipleOperator#compute(java.lang.Number,java.lang.Number)
     */
    @Override
    protected double evaluate(double value1, double value2) {
        List<Double> in = immutableList(value1, value2);
        return min.evaluate(immutableList(max.evaluate(in), minus
                .evaluate(immutableList(min.evaluate(in)))));
    }
}
