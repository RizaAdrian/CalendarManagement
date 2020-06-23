package com.uaic.service;

public interface NotificationService {

	void sendForgotPasswordNotification(String userToken, String email);

}
