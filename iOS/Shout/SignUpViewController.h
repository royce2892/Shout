//
//  SignUpViewController.h
//  OlaAppathon
//
//  Created by Ponnie Rohith on 07/02/15.
//  Copyright (c) 2015 PR. All rights reserved.
//
#import <CoreLocation/CoreLocation.h>


@interface SignUpViewController : UIViewController
@property (strong,nonatomic) CLLocationManager *locationManager;
@property (strong,nonatomic) CLGeocoder *geocoder;
@property (strong,nonatomic) CLPlacemark *placemark;
@property (strong,nonatomic) CLLocation *currentLocation;
@property (strong, nonatomic) IBOutlet UITextField *nameTextfield;
@property (strong, nonatomic) IBOutlet UITextField *passwordTextField;
- (IBAction)getCurrentLocation:(id)sender;

@end
