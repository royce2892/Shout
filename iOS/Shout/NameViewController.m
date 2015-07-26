//
//  NameViewController.m
//  MyGola
//
//  Created by Ponnie Rohith on 26/07/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "NameViewController.h"
#import "ServiceChatsViewController.h"
#import "Message.h"
@interface NameViewController () <UITextFieldDelegate>

@end

@implementation NameViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (IBAction)shoutTapped:(id)sender {
    if (self.nameTF.text.length > 0) {
        [self shout];
    }
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    if (self.nameTF.text.length > 0) {
        [self shout];
    }
    return YES;
}
-(void)shout
{
    [AppDelegate saveUserName:self.nameTF.text];
    Message *message = [[Message alloc]initWithText:@"shout" isSender:YES];
    [ParseDataHandler saveMessageInParse:message];
    [ParseDataHandler pushMessage:@"shout"];
    ServiceChatsViewController *chats = [ServiceChatsViewController new];
    [self.navigationController pushViewController:chats animated:YES];
  
}

@end
