using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Events;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class StartScript : MonoBehaviour
{

	/*
	 * 开始游戏按钮
	 */
	public Button StartButton;
	
	// Use this for initialization
	void Start () {
		StartButton.onClick.AddListener(startGame);
	}

	private void startGame()
	{
		SceneManager.LoadScene("MainScene");
	}
}
