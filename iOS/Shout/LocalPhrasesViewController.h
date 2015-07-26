//
//  LocalPhrasesViewController.h
//  TrackMyTrain
//
//  Created by Ponnie Rohith on 23/05/15.
//  Copyright (c) 2015 PR. All rights reserved.
//


@interface LocalPhrasesViewController : UIViewController < UITableViewDataSource , UITableViewDelegate>
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) NSString *location;
-(instancetype)initWithLocation:(NSString*)location;

@end
