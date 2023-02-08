import SwiftUI
import shared
import PDFKit

struct ContentView: View {
	let greet = Greeting().greet()
    
    let pdfDoc: PDFDocument
       
   init() {
       //for the sake of example, we're going to assume
       //you have a file Lipsum.pdf in your bundle
       let url = URL(string: PDFTool(fileURL: Bundle.main.url(forResource: "form_test", withExtension: "pdf")!).generatedFilePath)!
       let fileData = readDataFromUrl(with: url)
       
       pdfDoc = PDFDocument(data: fileData)!
   }

	var body: some View {
//		Text(greet)
        PDFKitView(showing: pdfDoc)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct PDFKitView: UIViewRepresentable {
    
    let pdfDocument: PDFDocument
    
    init(showing pdfDoc: PDFDocument) {
        self.pdfDocument = pdfDoc
    }
    
    //you could also have inits that take a URL or Data
    
    func makeUIView(context: Context) -> PDFView {
        let pdfView = PDFView()
        pdfView.document = pdfDocument
        pdfView.autoScales = true
        return pdfView
    }
    
    func updateUIView(_ pdfView: PDFView, context: Context) {
        pdfView.document = pdfDocument
    }
}

func readDataFromUrl(with url: URL) -> Data {
    // PathURL
    let fileURL = url
    // Read
    var readData = Data()
    do {
      readData = try Data(contentsOf: fileURL)
    } catch let jsonErr {
      print("Error read file:", jsonErr)
    }
    return readData
  }
