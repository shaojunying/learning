using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class MainButtonScript : MonoBehaviour
{

	/*
	 * 返回主菜单
	 */
	public Button BackMainButton;
	/*
	 * 将魔方打乱
	 */
	public Button ShuffleCubesButton;
	public Button ResetButton;
	public Button DemoSolveButton;
	public Button AbortButton;
	public Button ExportButton;
	public Button ImportButton;
	public Button ShowHistoryButton;
	public Button ClearHistoryButton;
	public Button SendCodesButton;
	

	
	// Use this for initialization
	void Start () {
		BackMainButton.onClick.AddListener(BackMain);
		ShuffleCubesButton.onClick.AddListener(ShuffleCubes);
		ResetButton.onClick.AddListener(Reset);
		DemoSolveButton.onClick.AddListener(DemoSolve);
		AbortButton.onClick.AddListener(Abort);
		ExportButton.onClick.AddListener(Export);
		ImportButton.onClick.AddListener(Import);
		ShowHistoryButton.onClick.AddListener(ShowHistory);
		ClearHistoryButton.onClick.AddListener(ClearHistory);
		SendCodesButton.onClick.AddListener(SendCodes);
	}
	private void ClearHistory()
	{
		throw new System.NotImplementedException();
	}

	private void ShowHistory()
	{
		throw new System.NotImplementedException();
	}

	private void Import()
	{
		throw new System.NotImplementedException();
	}

	private void Export()
	{
		throw new System.NotImplementedException();
	}

	private void Abort()
	{
		throw new System.NotImplementedException();
	}

	private void DemoSolve()
	{
		throw new System.NotImplementedException();
	}

	/*
	 * 重置魔方的状态
	 */
	private void Reset()
	{
		HelperScript.Reseting = true;
	}

	/*
	 * 将魔方随机进行打乱
	 */
	private void ShuffleCubes()
	{
		HelperScript.Shuffling=true;
		HelperScript.Steps = 10;
	}

	/*
	 * 返回主菜单
	 */
	private void BackMain()
	{
		SceneManager.LoadScene("StartScene");
	}

	private void SendCodes()
	{
		throw new System.NotImplementedException();
	}
}
