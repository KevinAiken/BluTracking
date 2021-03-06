# BluTracking

This project demonstrates how very inexpensive Bluetooth Low Energy tags can used to track assets with a $20 computer.

## How It Works

A Raspberry Pi runs a Python script telling it to get a list of Bluetooth addresses to survey for from the server, it surveys for them, and then uploads the ones it found out of the ones it surveyed for. The assets it's surveying for are BLE tags, reliably detectable from 50 feet away.

A RESTful web service provides access for the web client and Raspberry Pi to database information on logs and assets.

## Technology Stack

Web Client: Angular 6

Web Service: Spring

Database: MySQL

Hardware Scripting: Python

## Bluetooth Low Energy Tags

Bluetooth Low Energy is a recent specification of Bluetooth that allows a device to constantly advertise while using very little energy. The BLE tags we're using are Bytereal's and can be bought online for around $8 a piece. However, many BLE tags start are available for $2 and less in bulk. All BLE tags typically last around a year, advertising the entire time.

## Frontend Screenshots


List of assets
![Assets](/screenshots/asset_list.png "asset list") 


Individual assets page
![Individual Asset](/screenshots/individual_asset_page.png "single asset")

## Team

Kevin Aiken, Michael Hankins, Richard Marquet and Phu Pham created this project for GSU's Software Engineering class.
