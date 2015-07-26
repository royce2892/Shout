//
//  ServiceChatsViewController.h
//  Kaizen
//
//  Created by Ponnie Rohith on 28/03/15.
//  Copyright (c) 2015 PR. All rights reserved.
//


@interface ServiceChatsViewController : UIViewController <UITableViewDataSource, UITableViewDelegate
, UITextFieldDelegate , NSFetchedResultsControllerDelegate>
@property (strong, nonatomic) IBOutlet UITableView *tableview;
@property (strong, nonatomic) IBOutlet UITextField *messageTextField;

@end
