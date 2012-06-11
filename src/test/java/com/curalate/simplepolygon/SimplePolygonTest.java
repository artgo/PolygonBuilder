package com.curalate.simplepolygon;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;

import com.curalate.simplepolygon.exceptions.ParsingException;
import com.curalate.simplepolygon.exceptions.PolygonValidationException;
import com.curalate.simplepolygon.impl.InputParser;
import com.curalate.simplepolygon.impl.Point;
import com.curalate.simplepolygon.impl.Polygon;
import com.curalate.simplepolygon.impl.PolygonBuilder;
import com.curalate.simplepolygon.test.NullOutputStream;
import com.curalate.simplepolygon.test.StringOutputStream;
import com.curalate.simplepolygon.test.TestHelper;

public class SimplePolygonTest {

	@Test
	public void testParseIsCalled() throws ParsingException {
		final InputParser parserMock = mock(InputParser.class);
		final PolygonBuilder polygonBuilderMock = spy(new PolygonBuilder());

		final String[] params = TestHelper.TEST_INPUT;

		final SimplePolygon simplePolygon = new SimplePolygon(parserMock, polygonBuilderMock, new NullOutputStream());
		simplePolygon.process(params);

		verify(parserMock).parse(params);
	}

	@Test
	public void testBuildPolygonIsCalled() throws ParsingException, PolygonValidationException {
		final InputParser parserMock = mock(InputParser.class);
		final PolygonBuilder polygonBuilderMock = spy(new PolygonBuilder());

		final String[] params = TestHelper.TEST_INPUT;
		final Collection<Point> points = new LinkedList<>();

		doReturn(points).when(parserMock).parse(params);

		final SimplePolygon simplePolygon = new SimplePolygon(parserMock, polygonBuilderMock, new NullOutputStream());

		simplePolygon.process(params);

		verify(polygonBuilderMock).buildPolygon(points);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPrintsSimplePolygon() throws ParsingException, PolygonValidationException {
		final InputParser parserMock = mock(InputParser.class);
		final PolygonBuilder polygonBuilderMock = mock(PolygonBuilder.class);

		doReturn(new Polygon()).when(polygonBuilderMock).buildPolygon((Collection<Point>) argThat(is(anything())));

		final String[] params = TestHelper.TEST_INPUT;

		final StringOutputStream outputStream = new StringOutputStream();

		final SimplePolygon simplePolygon = new SimplePolygon(parserMock, polygonBuilderMock, outputStream);
		simplePolygon.process(params);

		assertTrue(outputStream.getString().contains("Simple Polygon:"));
	}

	@Test
	public void testProcessProcessesTestDataCorrectly() throws ParsingException, PolygonValidationException {
		final StringOutputStream outputStream = new StringOutputStream();
		final SimplePolygon simplePolygon = new SimplePolygon(new InputParser(), new PolygonBuilder(), outputStream);

		simplePolygon.process(TestHelper.TEST_INPUT);

		assertEquals("Simple Polygon: (0.00,0.00) (0.00,1.00) (1.00,1.00) (1.00,0.00)", outputStream.getString());
	}

	@Test
	public void testProcessProcessesTestData2Correctly() throws ParsingException, PolygonValidationException {
		final StringOutputStream outputStream = new StringOutputStream();
		final SimplePolygon simplePolygon = new SimplePolygon(new InputParser(), new PolygonBuilder(), outputStream);

		simplePolygon.process(TestHelper.TEST_INPUT2);

		assertEquals(
				"Simple Polygon: (1.00,1.00) (1.00,2.00) (1.00,3.00) (1.00,4.00) (2.00,4.00) (3.00,4.00) (4.00,4.00) (4.00,3.00) (4.00,2.00) (4.00,1.00) (3.00,1.00) (2.00,1.00)",
				outputStream.getString());
	}
}
