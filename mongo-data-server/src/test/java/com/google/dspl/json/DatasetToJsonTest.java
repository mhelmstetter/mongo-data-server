package com.google.dspl.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.dspl.model.DataType;
import com.google.dspl.model.Dataset;
import com.google.dspl.model.Info;
import com.google.dspl.model.Slice;
import com.google.dspl.model.Table;
import com.google.dspl.model.Table.Column;
import com.mongodb.Mongo;
import com.mongodb.opendata.repository.DatasetRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:infrastructure.xml")
public class DatasetToJsonTest {

    @Autowired
    Mongo mongo;
    
    @Autowired
    DatasetRepository datasetRepository;



    @Test
    public void test() {
        Dataset testDataset = constructDataset();
        datasetRepository.save(testDataset);
    }
    
    @Test
    public void testFind() {
        Dataset testDataset = constructDataset();
        datasetRepository.save(testDataset);
        Dataset d = datasetRepository.findOne("1");
        System.out.println(d);
        assertNotNull(d);
    }
    
    @Test
    public void testFindAll() {
        Dataset testDataset = constructDataset();
        datasetRepository.save(testDataset);
        Iterable<Dataset> i = datasetRepository.findAll();
        assertNotNull(i);
        assertTrue(i.iterator().hasNext());
        Dataset d = i.iterator().next();
        assertNotNull(d);
    }
    
    private Dataset constructDataset() {
        Dataset dataset = new Dataset();
        dataset.setDatasetId("1");
        dataset.setName("test");
        
        Info info = new Info();
        info.setName("Test Dataset");
        info.setDescription("Test Dataset Description");
        info.setUrl("http://test-dataset.com/test");
        dataset.setInfo(info);

        Info sliceInfo = new Info();
        sliceInfo.setName("Slice Name 1");
        sliceInfo.setDescription("Slice 1 description");
        Slice slice = new Slice();
        slice.setName("slice1");
        slice.setType("table");
        slice.setTable("popluation");
        slice.setInfo(sliceInfo);
        
        slice.addDimension("state");
        slice.addDimension("region");
        slice.addMetric("adjusted_gross_income");
        
        dataset.addSlice(slice);
        
        Table table = new Table();
        dataset.addTable(table);
        Info tableInfo = new Info();
        tableInfo.setName("population");
        table.setInfo(tableInfo);
        table.addSource("foo.csv");
        
        Column region = new Column();
        region.setId("REGION");
        region.setName("region");
        region.setType(DataType.STRING);
        table.addColumn(region);
        
        Column state = new Column();
        state.setId("STATE");
        state.setName("state");
        state.setType(DataType.STRING);
        table.addColumn(state);
        
        Column population = new Column();
        population.setId("GROSS INCOME");
        population.setName("adjusted_gross_income");
        population.setType(DataType.INTEGER);
        table.addColumn(population);
        
        
        return dataset;
      }

}
