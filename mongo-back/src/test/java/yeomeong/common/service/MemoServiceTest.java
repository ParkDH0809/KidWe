package yeomeong.common.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import yeomeong.common.document.Memo;
import yeomeong.common.repository.MemoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataMongoTest
class MemoServiceTest {

    private MemoRepository memoRepository;
    private MemoService memoService;

    @BeforeEach
    void setUp() {
        memoRepository = mock(MemoRepository.class);
        memoService = new MemoService(memoRepository); // 직접 주입
    }

    @Test
    void createMemo() {
        Memo memo = new Memo();
        when(memoRepository.save(any(Memo.class))).thenReturn(memo);

        Memo createdMemo = memoService.createMemo(memo);

        assertNotNull(createdMemo);
        verify(memoRepository, times(1)).save(any(Memo.class));
    }

    @Test
    void getMemosByDateRange() {
        Long teacherId = 1L;
        LocalDate date = LocalDate.now();
        List<Memo> memos = Arrays.asList(new Memo(), new Memo());
        when(memoRepository.findByTeacherIdAndDate(anyLong(), any(LocalDate.class))).thenReturn(memos);

        List<Memo> result = memoService.getMemosByTeacherIdAndDate(teacherId, date);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(memoRepository, times(1)).findByTeacherIdAndDate(anyLong(), any(LocalDate.class));
    }

    @Test
    void getMemosByTeacherIdAndDateRangeAndKidIds() {
        Long teacherId = 1L;
        LocalDate date = LocalDate.now();
        Long kidId = 2L;
        List<Memo> memos = Arrays.asList(new Memo(), new Memo());
        when(memoRepository.findByTeacherIdAndDateAndKidId(anyLong(), any(LocalDate.class), anyLong())).thenReturn(memos);

        List<Memo> result = memoService.getMemosByTeacherIdAndDateAndKidId(teacherId, date, kidId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(memoRepository, times(1)).findByTeacherIdAndDateAndKidId(anyLong(), any(LocalDate.class), anyLong());
    }

    @Test
    void updateMemo() {
        String id = "1";
        Memo existingMemo = new Memo();
        existingMemo.setUpdatedTime(LocalDateTime.now());
        existingMemo.setIsDeleted(false);
        existingMemo.setKids(Arrays.asList(1L, 2L));
        existingMemo.setTags(Arrays.asList("tag1", "tag2"));
        existingMemo.setContent("Old Content");

        Memo updatedMemo = new Memo();
        updatedMemo.setUpdatedTime(LocalDateTime.now());
        updatedMemo.setIsDeleted(false);
        updatedMemo.setKids(Arrays.asList(1L, 2L, 3L));
        updatedMemo.setTags(Arrays.asList("tag1", "tag2", "tag3"));
        updatedMemo.setContent("New Content");

        when(memoRepository.findById(anyString())).thenReturn(Optional.of(existingMemo));
        when(memoRepository.save(any(Memo.class))).thenReturn(updatedMemo);

        Memo result = memoService.updateMemo(id, updatedMemo);

        assertNotNull(result);
        assertEquals("New Content", result.getContent());
        assertEquals(Arrays.asList(1L, 2L, 3L), result.getKids());
        assertEquals(Arrays.asList("tag1", "tag2", "tag3"), result.getTags());
        verify(memoRepository, times(1)).findById(anyString());
        verify(memoRepository, times(1)).save(any(Memo.class));
    }

    @Test
    void deleteMemo() {
        String id = "1";
        doNothing().when(memoRepository).deleteById(anyString());

        memoService.deleteMemo(id);

        verify(memoRepository, times(1)).deleteById(anyString());
    }
}