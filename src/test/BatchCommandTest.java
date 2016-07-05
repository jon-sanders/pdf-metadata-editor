package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmpbox.xml.XmpParsingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pmedit.CommandLine;
import pmedit.MetadataInfo;
import pmedit.PDFMetadataEditBatch;
import pmedit.PDFMetadataEditBatch.ActionStatus;
import test.MetadataInfoTest.PMTuple;

public class BatchCommandTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClearAll() throws FileNotFoundException, IOException, XmpParsingException, Exception {
		List<PMTuple> fileList = MetadataInfoTest.randomFiles(100);
		List<String> args = new ArrayList<String>();
		args.add("clear");
		args.add("all");
		
		for(PMTuple t: fileList){
			args.add(t.file.getAbsolutePath());
		}
		
		CommandLine c = CommandLine.parse(args);
		PDFMetadataEditBatch batch =new PDFMetadataEditBatch(c.params);
		batch.runCommand(c.command, PDFMetadataEditBatch.fileList(c.fileList), new ActionStatus(){
			@Override
			public void addStatus(String filename, String message) {
			}

			@Override
			public void addError(String filename, String error) {
				System.out.println(error);
				assertFalse(error, true);
			}
			
		});
		MetadataInfo empty = new MetadataInfo();
		for(PMTuple t: fileList){
			MetadataInfo loaded = new MetadataInfo();
			loaded.loadFromPDF(t.file);
			//System.out.println(pdf.getAbsolutePath());
			assertTrue(empty.isEquvalent(loaded));
		}
	}

	@Test
	public void testClearNone() throws FileNotFoundException, IOException, XmpParsingException, Exception {
		List<PMTuple> fileList = MetadataInfoTest.randomFiles(100);
		List<String> args = new ArrayList<String>();
		args.add("clear");
		args.add("none");
		
		for(PMTuple t: fileList){
			args.add(t.file.getAbsolutePath());
		}
		
		CommandLine c = CommandLine.parse(args);
		PDFMetadataEditBatch batch =new PDFMetadataEditBatch(c.params);
		batch.runCommand(c.command, PDFMetadataEditBatch.fileList(c.fileList), new ActionStatus(){
			@Override
			public void addStatus(String filename, String message) {
			}

			@Override
			public void addError(String filename, String error) {
				System.out.println(error);
				assertFalse(error, true);
			}
			
		});
		MetadataInfo empty = new MetadataInfo();
		for(PMTuple t: fileList){
			MetadataInfo loaded = new MetadataInfo();
			loaded.loadFromPDF(t.file);
			//System.out.println(pdf.getAbsolutePath());
			assertTrue(t.md.isEquvalent(loaded));
		}
	}

	
}
