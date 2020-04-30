package com.OnlineEvent.umangburman.event.Models

data class MessageModel(val nickname: String? = null,
                        val message: String? = null,
                        val type: String? = null,
                        val fileFormat: String? = null,
                        val filePath: String? = null,
                        val fromUserId: Int = 0,
                        val toUserId: Int = 0,
                        val toSocketId: String? = null,
                        val time: String? = null,
                        val date: String? = null,
                        val imageUrl: String? = null)







