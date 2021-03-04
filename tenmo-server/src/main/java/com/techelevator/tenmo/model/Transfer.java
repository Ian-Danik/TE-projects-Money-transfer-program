package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

	private int transferID;
	private int transferType;
	private String typeName;
	private int transferStatus;
	private String statusName;
	private String senderName;
	private int sendingAccount;
	private int receivingAccount;
	private String receiverName;
	private BigDecimal amount;

	public int getTransferID() {
		return transferID;
	}

	public void setTransferID(int transferID) {
		this.transferID = transferID;
	}

	public int getTransferType() {
		return transferType;
	}

	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(int transferStatus) {
		this.transferStatus = transferStatus;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getSendingAccount() {
		return sendingAccount;
	}

	public void setSendingAccount(int sendingAccount) {
		this.sendingAccount = sendingAccount;
	}

	public int getReceivingAccount() {
		return receivingAccount;
	}

	public void setReceivingAccount(int receivingAccount) {
		this.receivingAccount = receivingAccount;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
