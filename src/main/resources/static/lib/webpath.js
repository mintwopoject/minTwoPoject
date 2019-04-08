// 服务器路径
var webpath="http://192.168.1.10:8080/";
// var webpath="http://47.105.40.28/";
// 弹出子窗口流程设计器
var openpath=webpath+"workflow_edit.html";
// 查看流程图
var openpathinfo=webpath+"workflow_info.html";
/**
 *  @url:coreworkflow/getApprovalUserids
 *  获取当前环节审批人集合
 */
var ApprovalUserids=webpath+"coreworkflow/getApprovalUserids";
/**
 *  @url:coreworkflow/getProcess_instance_info
 *  获取待办审批信息
 */
var ProcessInstanceInfo=webpath+"coreworkflow/getProcess_instance_info";
/**
 *  @url:coreworkflow/get_process_node_list
 *  获取下一环节列表
 */
var ProcessNodeList=webpath+"coreworkflow/get_process_node_list";
/**
 *  @url:coreworkflow/getUserTask
 *  获取待办列表
 */
var UserTask=webpath+"coreworkflow/getUserTask";
/**
 *  @url:coreworkflow/getHandling
 *  获取我的经办列表
 */
var Handling=webpath+"coreworkflow/getHandling";
/**
 *  @url:coreworkflow/getBeTriedForTrial
 *  获取我的被代审列表
 */
var BeTriedForTrial=webpath+"coreworkflow/getBeTriedForTrial";
/**
 *  @url:coreworkflow/getTrialForGenerations
 *  获取我的代审列表
 */
var TrialForGenerations=webpath+"coreworkflow/getTrialForGenerations";
/**
 *  @url:coreworkflow/advance_Process_Adopt
 *  流程推动-通过
 */
var ProcessAdopt=webpath+"coreworkflow/advance_Process_Adopt";
/**
 *  @url:coreworkflow/advance_Process_Reject
 *  流程推动-驳回
 */
var ProcessReject=webpath+"coreworkflow/advance_Process_Reject";
/**
 *  @url:coreworkflow/getProcess_Definition_All
 *  获全部取流程定义
 */
var ProcessDefinitionAll=webpath+"coreworkflow/getProcess_Definition_All";
/**
 *  @url:coreworkflow/getProcess_instance_approval
 *  获取流程审批意见
 */
var ProcessInstanceApproval=webpath+"coreworkflow/getProcess_instance_approval";
/**
 *  @url:coreworkflow/example_Process_Abolish
 *  实例流程废除
 */
var exampleProcessAbolish=webpath+"coreworkflow/example_Process_Abolish";
/**
 *  @url:coreworkflow/create_Process
 *  创建流程
 */
var CreateProcess=webpath+"coreworkflow/create_Process";
/**
 *  @url:coreworkflow/edit_process
 *  编辑流程定义
 */
var EditProcess=webpath+"coreworkflow/edit_process";
/**
 *  @url:coreworkflow/edit_workflow
 *  流程设计器
 */
var EditWorkflow=webpath+"coreworkflow/edit_workflow";
/**
 *  @url:coreworkflow/get_process_node_list_all
 *  获取流程环节列表
 */
var ProcessNodeListAll=webpath+"coreworkflow/get_process_node_list_all";
/**
 *  @url:coreworkflow/getProcess_Release
 *  发布流程
 */
var ProcessRelease=webpath+"coreworkflow/getProcess_Release";
/**
 *  @url:coreworkflow/definition_Process_Abolish
 *  流程废除
 */
var DefinitionProcessAbolish=webpath+"coreworkflow/definition_Process_Abolish";
/**
 *  @url:coreworkflow/definition_Process_Recovery
 *  定义流程恢复
 */
var DefinitionProcessRecovery=webpath+"coreworkflow/definition_Process_Recovery";













