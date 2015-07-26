//
//  ExploreViewController.h
//  MyGola
//
//  Created by Ponnie Rohith on 25/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

@interface ExploreViewController : UITabBarController

@property (strong, nonatomic) NSString *location;
-(instancetype)initWithLocation:(NSString*)location;

@end
