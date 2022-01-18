package com.untilled.roadcapture.network.subject

import com.untilled.roadcapture.network.observer.OAuthRefreshTokenExpirationObserver
import com.untilled.roadcapture.utils.manager.OAuthLoginManager

abstract class OAuthLoginManagerSubject: OAuthLoginManager, Subject<OAuthRefreshTokenExpirationObserver>() {

}