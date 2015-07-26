//
//  ServiceChatsViewController.m
//  Kaizen
//
//  Created by Ponnie Rohith on 28/03/15.
//  Copyright (c) 2015 PR. All rights reserved.
//

#import "ServiceChatsViewController.h"
#import "ChatDialogViewCell.h"
#import "Message.h"
#import "ParseDataHandler.h"
@interface ServiceChatsViewController ()

@end

BOOL keyboardIsShown = NO;
NSArray *messages;
@implementation ServiceChatsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.tableview.dataSource = self;
    self.tableview.delegate = self;
    [self.view addSubview:self.tableview];
    self.messageTextField.delegate = self;
    self.navigationItem.title = @"Shout";
    [self.navigationController.navigationBar setTitleTextAttributes:@{NSForegroundColorAttributeName: [UIColor colorWithRed:70/255.f green:135/255.f blue:256/255.f alpha:1.0]}];
//    //make contentSize bigger than your scrollSize (you will need to figure out for your own use case)
//    CGSize scrollContentSize = CGSizeMake(320, 345);
//    self.scrollView.contentSize = scrollContentSize;

    [self registerForKeyboardNotifs];
    [self refresh];

}
- (void)registerForKeyboardNotifs
{
    // register for keyboard notifications
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillShow:)
                                                 name:UIKeyboardWillShowNotification
                                               object:self.view.window];
    // register for keyboard notifications
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(keyboardWillHide:)
                                                 name:UIKeyboardWillHideNotification
                                               object:self.view.window];
    keyboardIsShown = NO;
 
}
- (void)keyboardWillHide:(NSNotification *)n
{
    NSDictionary* userInfo = [n userInfo];
    
    // get the size of the keyboard
    CGSize keyboardSize = [[userInfo objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    
    // resize the scrollview
    CGRect viewFrame = self.view.frame;
    // I'm also subtracting a constant kTabBarHeight because my UIScrollView was offset by the UITabBar so really only the portion of the keyboard that is leftover pass the UITabBar is obscuring my UIScrollView.
    viewFrame.size.height += (keyboardSize.height);
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [self.view setFrame:viewFrame];
    [UIView commitAnimations];
    
    keyboardIsShown = NO;
}

- (void)keyboardWillShow:(NSNotification *)n
{
    // This is an ivar I'm using to ensure that we do not do the frame size adjustment on the `UIScrollView` if the keyboard is already shown.  This can happen if the user, after fixing editing a `UITextField`, scrolls the resized `UIScrollView` to another `UITextField` and attempts to edit the next `UITextField`.  If we were to resize the `UIScrollView` again, it would be disastrous.  NOTE: The keyboard notification will fire even when the keyboard is already shown.
    if (keyboardIsShown) {
        return;
    }
    
    NSDictionary* userInfo = [n userInfo];
    
    // get the size of the keyboard
    CGSize keyboardSize = [[userInfo objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
    
    // resize the noteView
    CGRect viewFrame = self.view.frame;
    // I'm also subtracting a constant kTabBarHeight because my UIScrollView was offset by the UITabBar so really only the portion of the keyboard that is leftover pass the UITabBar is obscuring my UIScrollView.
    viewFrame.size.height -= (keyboardSize.height );
    
    [UIView beginAnimations:nil context:NULL];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [self.view setFrame:viewFrame];
    [UIView commitAnimations];
    keyboardIsShown = YES;
}
-(void)unregisterKeyboardNotifs
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
    [self unregisterKeyboardNotifs];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return messages.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    ChatDialogViewCell *cell;
    cell = [self.tableview dequeueReusableCellWithIdentifier:@"dialogCellId"];
    if (!cell) {
        cell = [[ChatDialogViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"dialogCellId"];
    }

    [cell configureCellWithMessage:messages[indexPath.row]];
    
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    return cell;
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return [ChatDialogViewCell heightForCellWithMessage:messages[indexPath.row]];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [self sendMessage];
    [textField resignFirstResponder];
    return YES;
}
- (IBAction)sendTapped:(id)sender
{
    [self sendMessage];
}
- (void)sendMessage
{
    NSString *text = self.messageTextField.text;
    if(text.length > 0 ){
        Message *message = [[Message alloc]initWithText:text isSender:YES];
        [ParseDataHandler saveMessageInParse:message];
        [ParseDataHandler pushMessage:text];
        [self refresh];
    }
    self.messageTextField.text = nil;
}
-(void)refresh
{
    [ParseDataHandler getAllMessagesWithCompBlock:[self getMessagesCompletion]];
    [self scrollToBottom];
}
-(void)scrollToBottom
{
    if (messages.count) {
        NSIndexPath *indexpath = [NSIndexPath indexPathForRow:messages.count - 1 inSection:0];
        [self.tableview scrollToRowAtIndexPath:indexpath atScrollPosition:UITableViewScrollPositionBottom animated:YES];

    }
}
-(arrayBlock)getMessagesCompletion
{
    return ^(NSArray* array){
        messages = array;
        [self.tableview reloadData];
    };
}

@end
