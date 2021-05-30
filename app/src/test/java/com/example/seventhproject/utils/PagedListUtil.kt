package com.example.seventhproject.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

object PagedListUtil {

    fun mockPagedList(list: List<*>): PagedList<*> {
        val pagedList = mock(PagedList::class.java) as PagedList<*>
        `when`(pagedList[anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}