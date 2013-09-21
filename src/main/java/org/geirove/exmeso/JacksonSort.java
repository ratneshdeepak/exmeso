package org.geirove.exmeso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.geirove.exmeso.ExternalMergeSort.SortHandler;

public class JacksonSort<T> implements ExternalMergeSort.SortHandler<T> {

    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper() {{
        configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    }};

    private final ObjectMapper mapper;
    private final Comparator<T> comparator;
    private final Class<T> type;

    public JacksonSort(Comparator<T> comparator, Class<T> type) {
        this(comparator, type, DEFAULT_MAPPER);
    }

    public JacksonSort(Comparator<T> comparator, Class<T> type, ObjectMapper mapper) {
        this.comparator = comparator;
        this.type = type;
        this.mapper = mapper;
    }

    @Override
    public void writeChunk(List<T> values, OutputStream out) throws IOException{
        for (T value : values) {
            writeChunkValue(value, out);
        }
    }

    @Override
    public void writeChunkValue(T value, OutputStream out) throws IOException{
        mapper.writeValue(out, value);
    }

    @Override
    public int compareChunks(T o1, T o2) {
        return comparator.compare(o1, o2);
    }
    
    @Override
    public void sortChunk(List<T> values) {
        Collections.sort(values, comparator);
    }

    @Override
    public Iterator<T> readValues(InputStream input) throws IOException {
        JsonFactory jfactory = mapper.getJsonFactory();
        JsonParser jParser = jfactory.createJsonParser(input);
        return jParser.readValuesAs(type);
    }

    @Override
    public void close() throws IOException {
    }

}