//
//  UserClient.swift
//  iosApp
//
//  Created by Beto on 28/4/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

public class UserClient {
    private init() {}
    
    public typealias UserHandler = (_ userInfo: User) -> Void
    
    public static let shared = UserClient()
    
    private let userPresenter = ServiceLocator.init().getUserPresenter
    
    private var handlerUser: UserHandler?
    
    public func fetchUserInformation(userID: String, completion: @escaping UserHandler) {
        userPresenter.fetchUserInformation(cb: self, userID: userID)
        handlerUser = completion
    }
    
}

// UserData is KMM callback to return user information fetched from API.
extension UserClient: UserData {
    public func onUserData(user: User) {
//        Logger().d(tag: TAG, message: user.first_name)
        self.handlerUser?(user)
    }
}
