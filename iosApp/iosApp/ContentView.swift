import SwiftUI
import shared
import PDFKit

struct ContentView: View {

    @StateObject var transactionViewModel = TransactionViewModel()

    @State private var clientName: String = ""
    @State private var amount: String = ""
    @State private var description: String = ""
    
	var body: some View {
        NavigationView {
            VStack(alignment: HorizontalAlignment.center, spacing: 32){
                
                let userInfo = transactionViewModel.userInformation
                
                Text("Transfer Money")
                
                if(userInfo != nil) {
                    VStack(alignment: HorizontalAlignment.leading, spacing: 4){
                        Text("From:")
                        Text("\((userInfo?.first_name)!) \((userInfo?.last_name)!)")
                    }
                }
                
                TextField(
                    "Client name",
                    text: $clientName
                )
                
                TextField(
                    "Amount",
                    text: $amount
                )
                
                TextField(
                    "Description",
                    text: $description
                )
                
                NavigationLink(destination: PDFKitView(information: ["clientName": clientName, "amount": amount, "description": description, "dateMonthDay": "10-02", "dateYear": "23"])) {
                                    Text("Submit")
                                }
            }
            .padding(.init(top: 0, leading: 24, bottom: 0, trailing: 24))
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct PDFKitView: UIViewRepresentable {
    
    let pdfDocument: PDFDocument
    
    init(information: [String : String]) {
        let url = URL(string: PDFTool(fileURL: Bundle.main.url(forResource: "void_cheque", withExtension: "pdf")!).fillForm(information: information))!
        let fileData = readDataFromUrl(with: url)
        self.pdfDocument = PDFDocument(data: fileData)!
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
