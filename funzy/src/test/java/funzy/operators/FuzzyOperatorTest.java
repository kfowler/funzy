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
package funzy.operators;

import static com.google.common.collect.Maps.immutableMap;
import static com.google.common.collect.Maps.newHashMap;
import static funzy.literals.SimpleDegree.HIGH;
import static funzy.literals.SimpleDegree.LOW;
import static funzy.literals.SimpleDegree.MEDIUM;
import static funzy.operators.FuzzyOperator.newOperator;
import static funzy.operators.FuzzyValueExtractor.newExtractor;
import static funzy.operators.functions.FuzzyFunctions.newMaxFunction;
import static funzy.operators.functions.FuzzyFunctions.newMinFunction;
import static funzy.operators.functions.FuzzyFunctions.newNotFuzzyFunction;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import funzy.literals.SimpleDegree;

/**
 * Test of the fuzzy operator.
 * 
 * @author <a href="romain.rouvoy+funzy@gmail.com">Romain Rouvoy</a>
 * @version $Revision$
 */
public class FuzzyOperatorTest {
	private Map<String,Map<SimpleDegree,Double>> input;
	private static final String VARIABLE = "degrees";
	
	@Before
	public void setup() {
		input = newHashMap();
		input.put(VARIABLE, immutableMap(LOW, (Double) .0, MEDIUM, .3, HIGH, .8));
	}
	
	@Test
	public void checkExtractor() {
		assertEquals(.0, newExtractor(VARIABLE, LOW, input).get());
		assertEquals(.3, newExtractor(VARIABLE, MEDIUM, input).get());
		assertEquals(.8, newExtractor(VARIABLE, HIGH, input).get());
	}
	
	@Test(expected=NullPointerException.class)
	public void checkExtractorVariableFailure() {
		assertEquals(.0, newExtractor("error", LOW, input).get());
	}

	@Test
	public void checkOperatorNot() {
		assertEquals(1.0, newOperator(newNotFuzzyFunction(), newExtractor(VARIABLE, LOW, input)).get());
		assertEquals(.7, newOperator(newNotFuzzyFunction(), newExtractor(VARIABLE, MEDIUM, input)).get());
		assertEquals(.2, newOperator(newNotFuzzyFunction(), newExtractor(VARIABLE, HIGH, input)).get(),.01);
	}

	@Test
	public void checkOperatorMin() {
		assertEquals(.3, newOperator(newMinFunction(), newExtractor(VARIABLE, MEDIUM, input),newExtractor(VARIABLE, HIGH, input)).get());
	}

	@Test
	public void checkOperatorMax() {
		assertEquals(.8, newOperator(newMaxFunction(), newExtractor(VARIABLE, MEDIUM, input),newExtractor(VARIABLE, HIGH, input)).get());
	}
}
