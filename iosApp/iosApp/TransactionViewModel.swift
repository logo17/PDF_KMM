//
//  TransactionViewModel.swift
//  iosApp
//
//  Created by Beto on 28/4/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class TransactionViewModel: ObservableObject {
    @Published var userInformation: User?
    
    init(){
        fetchUserInformation()
    }
    
    func fetchUserInformation() {
        UserClient.shared.fetchUserInformation(userID: "12") {userInfo in
            DispatchQueue.main.async {
                self.userInformation = userInfo
            }
        }
    }
}
