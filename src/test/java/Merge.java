import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.StreamSupport;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class Merge {

	public static void main(String[] args) throws Exception {
		PDFMergerUtility ut = new PDFMergerUtility();
		try (var paths = Files.newDirectoryStream(Paths.get(WatchDir.path), "*.pdf")) {
			StreamSupport.stream(paths.spliterator(), false)
		    .sorted(Comparator.comparing(Path::toString))
		    .forEach(path -> {
				try {
					ut.addSource(path.toFile());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			});
        }
		ut.setDestinationFileName("merged.pdf");
		ut.mergeDocuments(IOUtils.createMemoryOnlyStreamCache());
	}
}
